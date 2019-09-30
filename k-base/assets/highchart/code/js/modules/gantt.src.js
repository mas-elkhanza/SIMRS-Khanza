/**
 * @license Highcharts JS v6.1.2 (2018-08-31)
 * Gantt series
 *
 * (c) 2016 Lars A. V. Cabrera
 *
 * --- WORK IN PROGRESS ---
 *
 * License: www.highcharts.com/license
 */
'use strict';
(function (factory) {
	if (typeof module === 'object' && module.exports) {
		module.exports = factory;
	} else if (typeof define === 'function' && define.amd) {
		define(function () {
			return factory;
		});
	} else {
		factory(Highcharts);
	}
}(function (Highcharts) {
	(function (H) {
		/**
		* (c) 2016 Highsoft AS
		* Authors: Lars A. V. Cabrera
		*
		* License: www.highcharts.com/license
		*/

		var each = H.each,
		    isObject = H.isObject,
		    pick = H.pick,
		    wrap = H.wrap,
		    Axis = H.Axis,
		    Chart = H.Chart,
		    Tick = H.Tick;


		// Enum for which side the axis is on.
		// Maps to axis.side
		var axisSide = {
		    top: 0,
		    right: 1,
		    bottom: 2,
		    left: 3,
		    0: 'top',
		    1: 'right',
		    2: 'bottom',
		    3: 'left'
		};

		/**
		 * Checks if an axis is the outer axis in its dimension. Since
		 * axes are placed outwards in order, the axis with the highest
		 * index is the outermost axis.
		 *
		 * Example: If there are multiple x-axes at the top of the chart,
		 * this function returns true if the axis supplied is the last
		 * of the x-axes.
		 *
		 * @return true if the axis is the outermost axis in its dimension;
		 *         false if not
		 */
		Axis.prototype.isOuterAxis = function () {
		    var axis = this,
		        thisIndex = -1,
		        isOuter = true;

		    each(this.chart.axes, function (otherAxis, index) {
		        if (otherAxis.side === axis.side) {
		            if (otherAxis === axis) {
		                // Get the index of the axis in question
		                thisIndex = index;

		                // Check thisIndex >= 0 in case thisIndex has
		                // not been found yet
		            } else if (thisIndex >= 0 && index > thisIndex) {
		                // There was an axis on the same side with a
		                // higher index. Exit the loop.
		                isOuter = false;
		                return;
		            }
		        }
		    });
		    // There were either no other axes on the same side,
		    // or the other axes were not farther from the chart
		    return isOuter;
		};

		/**
		 * Shortcut function to Tick.label.getBBox().width.
		 *
		 * @return {number} width - the width of the tick label
		 */
		Tick.prototype.getLabelWidth = function () {
		    return this.label.getBBox().width;
		};

		/**
		 * Get the maximum label length.
		 * This function can be used in states where the axis.maxLabelLength has not
		 * been set.
		 *
		 * @param  {boolean} force - Optional parameter to force a new calculation, even
		 *                           if a value has already been set
		 * @return {number} maxLabelLength - the maximum label length of the axis
		 */
		Axis.prototype.getMaxLabelLength = function (force) {
		    var tickPositions = this.tickPositions,
		        ticks = this.ticks,
		        maxLabelLength = 0;

		    if (!this.maxLabelLength || force) {
		        each(tickPositions, function (tick) {
		            tick = ticks[tick];
		            if (tick && tick.labelLength > maxLabelLength) {
		                maxLabelLength = tick.labelLength;
		            }
		        });
		        this.maxLabelLength = maxLabelLength;
		    }
		    return this.maxLabelLength;
		};

		/**
		 * Adds the axis defined in axis.options.title
		 */
		Axis.prototype.addTitle = function () {
		    var axis = this,
		        renderer = axis.chart.renderer,
		        axisParent = axis.axisParent,
		        horiz = axis.horiz,
		        opposite = axis.opposite,
		        options = axis.options,
		        axisTitleOptions = options.title,
		        hasData,
		        showAxis,
		        textAlign;

		    // For reuse in Axis.render
		    hasData = axis.hasData();
		    axis.showAxis = showAxis = hasData || pick(options.showEmpty, true);

		    // Disregard title generation in original Axis.getOffset()
		    options.title = '';

		    if (!axis.axisTitle) {
		        textAlign = axisTitleOptions.textAlign;
		        if (!textAlign) {
		            textAlign = (horiz ? {
		                low: 'left',
		                middle: 'center',
		                high: 'right'
		            } : {
		                low: opposite ? 'right' : 'left',
		                middle: 'center',
		                high: opposite ? 'left' : 'right'
		            })[axisTitleOptions.align];
		        }
		        axis.axisTitle = renderer.text(
		            axisTitleOptions.text,
		            0,
		            0,
		            axisTitleOptions.useHTML
		        )
		        .attr({
		            zIndex: 7,
		            rotation: axisTitleOptions.rotation || 0,
		            align: textAlign
		        })
		        .addClass('highcharts-axis-title')
        
		        // Add to axisParent instead of axisGroup, to ignore the space
		        // it takes
		        .add(axisParent);
		        axis.axisTitle.isNew = true;
		    }


		    // hide or show the title depending on whether showEmpty is set
		    axis.axisTitle[showAxis ? 'show' : 'hide'](true);
		};

		/**
		 * Add custom date formats
		 */
		H.dateFormats = {
		    // Week number
		    W: function (timestamp) {
		        var date = new this.Date(timestamp),
		            day = this.get('Day', date) === 0 ? 7 : this.get('Day', date),
		            time = date.getTime(),
		            startOfYear = new Date(this.get('FullYear', date), 0, 1, -6),
		            dayNumber;
		        this.set('Date', date, this.get('Date', date) + 4 - day);
		        dayNumber = Math.floor((time - startOfYear) / 86400000);
		        return 1 + Math.floor(dayNumber / 7);
		    },
		    // First letter of the day of the week, e.g. 'M' for 'Monday'.
		    E: function (timestamp) {
		        return this.dateFormat('%a', timestamp, true).charAt(0);
		    }
		};

		/**
		 * Prevents adding the last tick label if the axis is not a category axis.
		 *
		 * Since numeric labels are normally placed at starts and ends of a range of
		 * value, and this module makes the label point at the value, an "extra" label
		 * would appear.
		 *
		 * @param {function} proceed - the original function
		 */
		wrap(Tick.prototype, 'addLabel', function (proceed) {
		    var axis = this.axis,
		        isCategoryAxis = axis.options.categories !== undefined,
		        tickPositions = axis.tickPositions,
		        lastTick = tickPositions[tickPositions.length - 1],
		        isLastTick = this.pos !== lastTick;

		    if (!axis.options.grid || isCategoryAxis || isLastTick) {
		        proceed.apply(this);
		    }
		});

		/**
		 * Center tick labels vertically and horizontally between ticks
		 *
		 * @param {function} proceed - the original function
		 *
		 * @return {object} object - an object containing x and y positions
		 *                         for the tick
		 */
		wrap(Tick.prototype, 'getLabelPosition', function (proceed, x, y, label) {
		    var retVal = proceed.apply(this, Array.prototype.slice.call(arguments, 1)),
		        axis = this.axis,
		        options = axis.options,
		        tickInterval = options.tickInterval || 1,
		        newX,
		        newPos,
		        axisHeight,
		        fontSize,
		        labelMetrics,
		        lblB,
		        lblH,
		        labelCenter;

		    // Only center tick labels if axis has option grid: true
		    if (options.grid) {
		        fontSize = options.labels.style.fontSize;
		        labelMetrics = axis.chart.renderer.fontMetrics(fontSize, label);
		        lblB = labelMetrics.b;
		        lblH = labelMetrics.h;

		        if (axis.horiz && options.categories === undefined) {
		            // Center x position
		            axisHeight = axis.axisGroup.getBBox().height;
		            newPos = this.pos + tickInterval / 2;
		            retVal.x = axis.translate(newPos) + axis.left;
		            labelCenter = (axisHeight / 2) + (lblH / 2) - Math.abs(lblH - lblB);

		            // Center y position
		            if (axis.side === axisSide.top) {
		                retVal.y = y - labelCenter;
		            } else {
		                retVal.y = y + labelCenter;
		            }
		        } else {
		            // Center y position
		            if (options.categories === undefined) {
		                newPos = this.pos + (tickInterval / 2);
		                retVal.y = axis.translate(newPos) + axis.top + (lblB / 2);
		            }

		            // Center x position
		            newX = (this.getLabelWidth() / 2) - (axis.maxLabelLength / 2);
		            if (axis.side === axisSide.left) {
		                retVal.x += newX;
		            } else {
		                retVal.x -= newX;
		            }
		        }
		    }
		    return retVal;
		});


		/**
		 * Draw vertical ticks extra long to create cell floors and roofs.
		 * Overrides the tickLength for vertical axes.
		 *
		 * @param {function} proceed - the original function
		 * @returns {array} retVal -
		 */
		wrap(Axis.prototype, 'tickSize', function (proceed) {
		    var axis = this,
		        retVal = proceed.apply(axis, Array.prototype.slice.call(arguments, 1)),
		        labelPadding,
		        distance;

		    if (axis.options.grid && !axis.horiz) {
		        labelPadding = (Math.abs(axis.defaultLeftAxisOptions.labels.x) * 2);
		        if (!axis.maxLabelLength) {
		            axis.maxLabelLength = axis.getMaxLabelLength();
		        }
		        distance = axis.maxLabelLength + labelPadding;

		        retVal[0] = distance;
		    }
		    return retVal;
		});

		/**
		 * Disregards space required by axisTitle, by adding axisTitle to axisParent
		 * instead of axisGroup, and disregarding margins and offsets related to
		 * axisTitle.
		 *
		 * @param {function} proceed - the original function
		 */
		wrap(Axis.prototype, 'getOffset', function (proceed) {
		    var axis = this,
		        axisOffset = axis.chart.axisOffset,
		        side = axis.side,
		        axisHeight,
		        tickSize,
		        options = axis.options,
		        axisTitleOptions = options.title,
		        addTitle = axisTitleOptions &&
		                axisTitleOptions.text &&
		                axisTitleOptions.enabled !== false;

		    if (axis.options.grid && isObject(axis.options.title)) {

		        tickSize = axis.tickSize('tick')[0];
		        if (axisOffset[side] && tickSize) {
		            axisHeight = axisOffset[side] + tickSize;
		        }

		        if (addTitle) {
		            // Use the custom addTitle() to add it, while preventing making room
		            // for it
		            axis.addTitle();
		        }

		        proceed.apply(axis, Array.prototype.slice.call(arguments, 1));

		        axisOffset[side] = pick(axisHeight, axisOffset[side]);


		        // Put axis options back after original Axis.getOffset() has been called
		        options.title = axisTitleOptions;

		    } else {
		        proceed.apply(axis, Array.prototype.slice.call(arguments, 1));
		    }
		});

		/**
		 * Prevents rotation of labels when squished, as rotating them would not
		 * help.
		 *
		 * @param {function} proceed - the original function
		 */
		wrap(Axis.prototype, 'renderUnsquish', function (proceed) {
		    if (this.options.grid) {
		        this.labelRotation = 0;
		        this.options.labels.rotation = 0;
		    }
		    proceed.apply(this);
		});

		/**
		 * Places leftmost tick at the start of the axis, to create a left wall.
		 *
		 * @param {function} proceed - the original function
		 */
		wrap(Axis.prototype, 'setOptions', function (proceed, userOptions) {
		    var axis = this;
		    if (userOptions.grid && axis.horiz) {
		        userOptions.startOnTick = true;
		        userOptions.minPadding = 0;
		        userOptions.endOnTick = true;
		    }
		    proceed.apply(this, Array.prototype.slice.call(arguments, 1));
		});

		/**
		 * Draw an extra line on the far side of the the axisLine,
		 * creating cell roofs of a grid.
		 *
		 * @param {function} proceed - the original function
		 */
		wrap(Axis.prototype, 'render', function (proceed) {
		    var axis = this,
		        options = axis.options,
		        labelPadding,
		        distance,
		        lineWidth,
		        linePath,
		        yStartIndex,
		        yEndIndex,
		        xStartIndex,
		        xEndIndex,
		        renderer = axis.chart.renderer,
		        axisGroupBox;

		    if (options.grid) {
		        labelPadding = (Math.abs(axis.defaultLeftAxisOptions.labels.x) * 2);
		        distance = axis.maxLabelLength + labelPadding;
		        lineWidth = options.lineWidth;

		        // Remove right wall before rendering
		        if (axis.rightWall) {
		            axis.rightWall.destroy();
		        }

		        // Call original Axis.render() to obtain axis.axisLine and
		        // axis.axisGroup
		        proceed.apply(axis);

		        axisGroupBox = axis.axisGroup.getBBox();

		        // Add right wall on horizontal axes
		        if (axis.horiz) {
		            axis.rightWall = renderer.path([
		                'M',
		                axisGroupBox.x + axis.width + 1, // account for left wall
		                axisGroupBox.y,
		                'L',
		                axisGroupBox.x + axis.width + 1, // account for left wall
		                axisGroupBox.y + axisGroupBox.height
		            ])
		            .attr({
		                stroke: options.tickColor || '#ccd6eb',
		                'stroke-width': options.tickWidth || 1,
		                zIndex: 7,
		                class: 'grid-wall'
		            })
		            .add(axis.axisGroup);
		        }

		        if (axis.isOuterAxis() && axis.axisLine) {
		            if (axis.horiz) {
		                // -1 to avoid adding distance each time the chart updates
		                distance = axisGroupBox.height - 1;
		            }

		            if (lineWidth) {
		                linePath = axis.getLinePath(lineWidth);
		                xStartIndex = linePath.indexOf('M') + 1;
		                xEndIndex = linePath.indexOf('L') + 1;
		                yStartIndex = linePath.indexOf('M') + 2;
		                yEndIndex = linePath.indexOf('L') + 2;

		                // Negate distance if top or left axis
		                if (axis.side === axisSide.top || axis.side === axisSide.left) {
		                    distance = -distance;
		                }

		                // If axis is horizontal, reposition line path vertically
		                if (axis.horiz) {
		                    linePath[yStartIndex] = linePath[yStartIndex] + distance;
		                    linePath[yEndIndex] = linePath[yEndIndex] + distance;
		                } else {
		                    // If axis is vertical, reposition line path horizontally
		                    linePath[xStartIndex] = linePath[xStartIndex] + distance;
		                    linePath[xEndIndex] = linePath[xEndIndex] + distance;
		                }

		                if (!axis.axisLineExtra) {
		                    axis.axisLineExtra = renderer.path(linePath)
		                        .attr({
		                            stroke: options.lineColor,
		                            'stroke-width': lineWidth,
		                            zIndex: 7
		                        })
		                        .add(axis.axisGroup);
		                } else {
		                    axis.axisLineExtra.animate({
		                        d: linePath
		                    });
		                }

		                // show or hide the line depending on options.showEmpty
		                axis.axisLine[axis.showAxis ? 'show' : 'hide'](true);
		            }
		        }
		    } else {
		        proceed.apply(axis);
		    }
		});

		/**
		 * Wraps chart rendering with the following customizations:
		 * 1. Prohibit timespans of multitudes of a time unit
		 * 2. Draw cell walls on vertical axes
		 *
		 * @param {function} proceed - the original function
		 */
		wrap(Chart.prototype, 'render', function (proceed) {
		    // 25 is optimal height for default fontSize (11px)
		    // 25 / 11 ≈ 2.28
		    var fontSizeToCellHeightRatio = 25 / 11,
		        fontMetrics,
		        fontSize;

		    each(this.axes, function (axis) {
		        var options = axis.options;
		        if (options.grid) {
		            fontSize = options.labels.style.fontSize;
		            fontMetrics = axis.chart.renderer.fontMetrics(fontSize);

		            // Prohibit timespans of multitudes of a time unit,
		            // e.g. two days, three weeks, etc.
		            if (options.type === 'datetime') {
		                options.units = [
		                    ['millisecond', [1]],
		                    ['second', [1]],
		                    ['minute', [1]],
		                    ['hour', [1]],
		                    ['day', [1]],
		                    ['week', [1]],
		                    ['month', [1]],
		                    ['year', null]
		                ];
		            }

		            // Make tick marks taller, creating cell walls of a grid.
		            // Use cellHeight axis option if set
		            if (axis.horiz) {
		                options.tickLength = options.cellHeight ||
		                        fontMetrics.h * fontSizeToCellHeightRatio;
		            } else {
		                options.tickWidth = 1;
		                if (!options.lineWidth) {
		                    options.lineWidth = 1;
		                }
		            }
		        }
		    });

		    // Call original Chart.render()
		    proceed.apply(this);
		});

	}(Highcharts));
	(function (H) {
		/**
		 * X-range series module
		 *
		 * (c) 2010-2017 Torstein Honsi, Lars A. V. Cabrera
		 *
		 * License: www.highcharts.com/license
		 */


		var addEvent = H.addEvent,
		    defined = H.defined,
		    color = H.Color,
		    columnType = H.seriesTypes.column,
		    each = H.each,
		    isNumber = H.isNumber,
		    isObject = H.isObject,
		    merge = H.merge,
		    pick = H.pick,
		    seriesType = H.seriesType,
		    seriesTypes = H.seriesTypes,
		    Axis = H.Axis,
		    Point = H.Point,
		    Series = H.Series;

		/**
		 * The X-range series displays ranges on the X axis, typically time intervals
		 * with a start and end date.
		 *
		 * @extends      {plotOptions.column}
		 * @excluding    boostThreshold,crisp,cropThreshold,depth,edgeColor,edgeWidth,
		 *               findNearestPointBy,getExtremesFromAll,grouping,groupPadding,
		 *               negativeColor,pointInterval,pointIntervalUnit,pointPlacement,
		 *               pointRange,pointStart,softThreshold,stacking,threshold,data
		 * @product      highcharts highstock
		 * @sample       {highcharts} highcharts/demo/x-range/
		 *               X-range
		 * @sample       {highcharts} highcharts/css/x-range/
		 *               Styled mode X-range
		 * @sample       {highcharts} highcharts/chart/inverted-xrange/
		 *               Inverted X-range
		 * @since        6.0.0
		 * @optionparent plotOptions.xrange
		 */
		seriesType('xrange', 'column', {
		    /**
		     * A partial fill for each point, typically used to visualize how much of
		     * a task is performed. The partial fill object can be set either on series
		     * or point level.
		     *
		     * @sample    {highcharts} highcharts/demo/x-range
		     *            X-range with partial fill
		     * @type      {Object}
		     * @product   highcharts highstock
		     * @apioption plotOptions.xrange.partialFill
		     */

		    /**
		     * The fill color to be used for partial fills. Defaults to a darker shade
		     * of the point color.
		     *
		     * @type      {Color}
		     * @product   highcharts highstock
		     * @apioption plotOptions.xrange.partialFill.fill
		     */

		    /**
		     * In an X-range series, this option makes all points of the same Y-axis
		     * category the same color.
		     */
		    colorByPoint: true,
		    dataLabels: {
		        verticalAlign: 'middle',
		        inside: true,
		        /**
		         * The default formatter for X-range data labels displays the percentage
		         * of the partial fill amount.
		         */
		        formatter: function () {
		            var point = this.point,
		                amount = point.partialFill;
		            if (isObject(amount)) {
		                amount = amount.amount;
		            }
		            if (!defined(amount)) {
		                amount = 0;
		            }
		            return (amount * 100) + '%';
		        }
		    },
		    tooltip: {
		        headerFormat: '<span style="font-size: 0.85em">{point.x} - {point.x2}</span><br/>',
		        pointFormat: '<span style="color:{point.color}">\u25CF</span> {series.name}: <b>{point.yCategory}</b><br/>'
		    },
		    borderRadius: 3,
		    pointRange: 0

		}, {
		    type: 'xrange',
		    parallelArrays: ['x', 'x2', 'y'],
		    requireSorting: false,
		    animate: seriesTypes.line.prototype.animate,
		    cropShoulder: 1,
		    getExtremesFromAll: true,

		    /**
		     * Borrow the column series metrics, but with swapped axes. This gives free
		     * access to features like groupPadding, grouping, pointWidth etc.
		     */
		    getColumnMetrics: function () {
		        var metrics,
		            chart = this.chart;

		        function swapAxes() {
		            each(chart.series, function (s) {
		                var xAxis = s.xAxis;
		                s.xAxis = s.yAxis;
		                s.yAxis = xAxis;
		            });
		        }

		        swapAxes();

		        metrics = columnType.prototype.getColumnMetrics.call(this);

		        swapAxes();

		        return metrics;
		    },

		    /**
		     * Override cropData to show a point where x or x2 is outside visible range,
		     * but one of them is inside.
		     */
		    cropData: function (xData, yData, min, max) {

		        // Replace xData with x2Data to find the appropriate cropStart
		        var cropData = Series.prototype.cropData,
		            crop = cropData.call(this, this.x2Data, yData, min, max);

		        // Re-insert the cropped xData
		        crop.xData = xData.slice(crop.start, crop.end);

		        return crop;
		    },

		    translatePoint: function (point) {
		        var series = this,
		            xAxis = series.xAxis,
		            metrics = series.columnMetrics,
		            minPointLength = series.options.minPointLength || 0,
		            plotX = point.plotX,
		            posX = pick(point.x2, point.x + (point.len || 0)),
		            plotX2 = xAxis.translate(posX, 0, 0, 0, 1),
		            length = plotX2 - plotX,
		            widthDifference,
		            shapeArgs,
		            partialFill,
		            inverted = this.chart.inverted,
		            borderWidth = pick(series.options.borderWidth, 1),
		            crisper = borderWidth % 2 / 2,
		            dlLeft,
		            dlRight,
		            dlWidth;

		        if (minPointLength) {
		            widthDifference = minPointLength - length;
		            if (widthDifference < 0) {
		                widthDifference = 0;
		            }
		            plotX -= widthDifference / 2;
		            plotX2 += widthDifference / 2;
		        }

		        plotX = Math.max(plotX, -10);
		        plotX2 = Math.min(Math.max(plotX2, -10), xAxis.len + 10);

		        point.shapeArgs = {
		            x: Math.floor(Math.min(plotX, plotX2)) + crisper,
		            y: Math.floor(point.plotY + metrics.offset) + crisper,
		            width: Math.round(Math.abs(plotX2 - plotX)),
		            height: Math.round(metrics.width),
		            r: series.options.borderRadius
		        };

		        // Align data labels inside the shape and inside the plot area
		        dlLeft = point.shapeArgs.x;
		        dlRight = dlLeft + point.shapeArgs.width;
		        if (dlLeft < 0 || dlRight > xAxis.len) {
		            dlLeft = Math.min(xAxis.len, Math.max(0, dlLeft));
		            dlRight = Math.max(0, Math.min(dlRight, xAxis.len));
		            dlWidth = dlRight - dlLeft;
		            point.dlBox = merge(point.shapeArgs, {
		                x: dlLeft,
		                width: dlRight - dlLeft,
		                centerX: dlWidth ? dlWidth / 2 : null
		            });

		        } else {
		            point.dlBox = null;
		        }

		        // Tooltip position
		        point.tooltipPos[0] += inverted ? 0 : length / 2;
		        point.tooltipPos[1] -= inverted ? length / 2 : metrics.width / 2;

		        // Add a partShapeArgs to the point, based on the shapeArgs property
		        partialFill = point.partialFill;
		        if (partialFill) {
		            // Get the partial fill amount
		            if (isObject(partialFill)) {
		                partialFill = partialFill.amount;
		            }
		            // If it was not a number, assume 0
		            if (!isNumber(partialFill)) {
		                partialFill = 0;
		            }
		            shapeArgs = point.shapeArgs;
		            point.partShapeArgs = {
		                x: shapeArgs.x,
		                y: shapeArgs.y,
		                width: shapeArgs.width,
		                height: shapeArgs.height,
		                r: series.options.borderRadius
		            };
		            point.clipRectArgs = {
		                x: shapeArgs.x,
		                y: shapeArgs.y,
		                width: Math.max(
		                    Math.round(
		                        length * partialFill +
		                        (point.plotX - plotX)
		                    ),
		                    0
		                ),
		                height: shapeArgs.height
		            };
		        }
		    },

		    translate: function () {
		        columnType.prototype.translate.apply(this, arguments);
		        each(this.points, function (point) {
		            this.translatePoint(point);
		        }, this);
		    },

		    /**
		     * Draws a single point in the series. Needed for partial fill.
		     *
		     * This override turns point.graphic into a group containing the original
		     * graphic and an overlay displaying the partial fill.
		     *
		     * @param   {Object} point an instance of Point in the series
		     * @param   {string} verb 'animate' (animates changes) or 'attr' (sets
		     *                   options)
		     * @returns {void}
		     */
		    drawPoint: function (point, verb) {
		        var series = this,
		            seriesOpts = series.options,
		            renderer = series.chart.renderer,
		            graphic = point.graphic,
		            type = point.shapeType,
		            shapeArgs = point.shapeArgs,
		            partShapeArgs = point.partShapeArgs,
		            clipRectArgs = point.clipRectArgs,
		            pfOptions = point.partialFill,
		            fill,
		            state = point.selected && 'select',
		            cutOff = seriesOpts.stacking && !seriesOpts.borderRadius;

		        if (!point.isNull) {

		            // Original graphic
		            if (graphic) { // update
		                point.graphicOriginal[verb](
		                    merge(shapeArgs)
		                );

		            } else {
		                point.graphic = graphic = renderer.g('point')
		                    .addClass(point.getClassName())
		                    .add(point.group || series.group);

		                point.graphicOriginal = renderer[type](shapeArgs)
		                    .addClass(point.getClassName())
		                    .addClass('highcharts-partfill-original')
		                    .add(graphic);
		            }

		            // Partial fill graphic
		            if (partShapeArgs) {
		                if (point.graphicOverlay) {
		                    point.graphicOverlay[verb](
		                        merge(partShapeArgs)
		                    );
		                    point.clipRect.animate(
		                        merge(clipRectArgs)
		                    );

		                } else {

		                    point.clipRect = renderer.clipRect(
		                        clipRectArgs.x,
		                        clipRectArgs.y,
		                        clipRectArgs.width,
		                        clipRectArgs.height
		                    );

		                    point.graphicOverlay = renderer[type](partShapeArgs)
		                        .addClass('highcharts-partfill-overlay')
		                        .add(graphic)
		                        .clip(point.clipRect);
		                }
		            }


            

		        } else if (graphic) {
		            point.graphic = graphic.destroy(); // #1269
		        }
		    },

		    drawPoints: function () {
		        var series = this,
		            verb = series.getAnimationVerb();

		        // Draw the columns
		        each(series.points, function (point) {
		            series.drawPoint(point, verb);
		        });
		    },


		    /**
		     * Returns "animate", or "attr" if the number of points is above the
		     * animation limit.
		     *
		     * @returns {String}
		     */
		    getAnimationVerb: function () {
		        return this.chart.pointCount < (this.options.animationLimit || 250) ?
		             'animate' : 'attr';
		    }

		    /**
		     * Override to remove stroke from points.
		     * For partial fill.
		     * /
		    pointAttribs: function () {
		        var series = this,
		            retVal = columnType.prototype.pointAttribs.apply(series, arguments);

		        //retVal['stroke-width'] = 0;
		        return retVal;
		    }
		    //*/

		// Point class properties
		}, {

		    /**
		     * Extend init so that `colorByPoint` for x-range means that one color is
		     * applied per Y axis category.
		     */
		    init: function () {

		        Point.prototype.init.apply(this, arguments);

		        var colors,
		            series = this.series,
		            colorCount = series.chart.options.chart.colorCount;

		        if (!this.y) {
		            this.y = 0;
		        }

        
		        this.colorIndex = pick(this.options.colorIndex, this.y % colorCount);

		        return this;
		    },

		    setState: function () {
		        Point.prototype.setState.apply(this, arguments);

		        this.series.drawPoint(this, this.series.getAnimationVerb());
		    },

		    // Add x2 and yCategory to the available properties for tooltip formats
		    getLabelConfig: function () {
		        var point = this,
		            cfg = Point.prototype.getLabelConfig.call(point),
		            yCats = point.series.yAxis.categories;

		        cfg.x2 = point.x2;
		        cfg.yCategory = point.yCategory = yCats && yCats[point.y];
		        return cfg;
		    },
		    tooltipDateKeys: ['x', 'x2'],

		    isValid: function () {
		        return typeof this.x === 'number' &&
		            typeof this.x2 === 'number';
		    }
		});

		/**
		 * Max x2 should be considered in xAxis extremes
		 */
		addEvent(Axis, 'afterGetSeriesExtremes', function () {
		    var axis = this,
		        axisSeries = axis.series,
		        dataMax,
		        modMax;

		    if (axis.isXAxis) {
		        dataMax = pick(axis.dataMax, -Number.MAX_VALUE);
		        each(axisSeries, function (series) {
		            if (series.x2Data) {
		                each(series.x2Data, function (val) {
		                    if (val > dataMax) {
		                        dataMax = val;
		                        modMax = true;
		                    }
		                });
		            }
		        });
		        if (modMax) {
		            axis.dataMax = dataMax;
		        }
		    }
		});


		/**
		 * An `xrange` series. If the [type](#series.xrange.type) option is not
		 * specified, it is inherited from [chart.type](#chart.type).
		 *
		 * @type      {Object}
		 * @extends   series,plotOptions.xrange
		 * @excluding boostThreshold,crisp,cropThreshold,depth,edgeColor,edgeWidth,
		 *            findNearestPointBy,getExtremesFromAll,grouping,groupPadding,
		 *            negativeColor,pointInterval,pointIntervalUnit,pointPlacement,
		 *            pointRange,pointStart,softThreshold,stacking,threshold
		 * @product   highcharts highstock
		 * @apioption series.xrange
		 */

		/**
		 * An array of data points for the series. For the `xrange` series type,
		 * points can be given in the following ways:
		 *
		 * 1.  An array of objects with named values. The objects are point
		 * configuration objects as seen below.
		 *
		 *  ```js
		 *     data: [{
		 *         x: Date.UTC(2017, 0, 1),
		 *         x2: Date.UTC(2017, 0, 3),
		 *         name: "Test",
		 *         y: 0,
		 *         color: "#00FF00"
		 *     }, {
		 *         x: Date.UTC(2017, 0, 4),
		 *         x2: Date.UTC(2017, 0, 5),
		 *         name: "Deploy",
		 *         y: 1,
		 *         color: "#FF0000"
		 *     }]
		 *  ```
		 *
		 * @type      {Array<Object|Array|Number>}
		 * @extends   series.line.data
		 * @sample    {highcharts} highcharts/chart/reflow-true/
		 *            Numerical values
		 * @sample    {highcharts} highcharts/series/data-array-of-arrays/
		 *            Arrays of numeric x and y
		 * @sample    {highcharts} highcharts/series/data-array-of-arrays-datetime/
		 *            Arrays of datetime x and y
		 * @sample    {highcharts} highcharts/series/data-array-of-name-value/
		 *            Arrays of point.name and y
		 * @sample    {highcharts} highcharts/series/data-array-of-objects/
		 *            Config objects
		 * @product   highcharts highstock
		 * @apioption series.xrange.data
		 */

		/**
		 * The ending X value of the range point.
		 *
		 * @sample    {highcharts} highcharts/demo/x-range
		 *            X-range
		 * @type      {Number}
		 * @product   highcharts highstock
		 * @apioption plotOptions.xrange.data.x2
		 */

		/**
		 * A partial fill for each point, typically used to visualize how much of
		 * a task is performed. The partial fill object can be set either on series
		 * or point level.
		 *
		 * @sample    {highcharts} highcharts/demo/x-range
		 *            X-range with partial fill
		 * @type      {Object|Number}
		 * @product   highcharts highstock
		 * @apioption plotOptions.xrange.data.partialFill
		 */

		/**
		 * The amount of the X-range point to be filled. Values can be 0-1 and are
		 * converted to percentages in the default data label formatter.
		 *
		 * @type      {Number}
		 * @product   highcharts highstock
		 * @apioption plotOptions.xrange.data.partialFill.amount
		 */

		/**
		 * The fill color to be used for partial fills. Defaults to a darker shade
		 * of the point color.
		 *
		 * @type      {Color}
		 * @product   highcharts highstock
		 * @apioption plotOptions.xrange.data.partialFill.fill
		 */

	}(Highcharts));
	(function () {
		/**
		* (c) 2016 Highsoft AS
		* Authors: Lars A. V. Cabrera
		*
		* License: www.highcharts.com/license
		*/
		// 
	}());
	return (function () {


	}());
}));
