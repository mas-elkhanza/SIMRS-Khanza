var theme = {

    color: [
        '#2ec7c9','#b6a2de','#0cc2aa','#6887ff','#6cc788',
        '#f77a99','#f44455','#97b552','#95706d','#dc69aa',
        '#07a2a4','#9a7fd1','#588dd5','#f5994e','#c05050',
        '#59678c','#c9ab00','#7eb00a','#6f5553','#c14089'
    ],

    title: {
        textStyle: {
            fontWeight: 'normal',
            color: '#008acd'
        }
    },

    legend: {
        textStyle: {
            color: '#aaa'
        }
    },
    
    dataRange: {
        itemWidth: 15,
        color: ['#2ec7c9','#b6a2de'],
        textStyle: {
            color: '#ccc'
        }
    },

    toolbox: {
        color : ['#1e90ff', '#1e90ff', '#1e90ff', '#1e90ff'],
        effectiveColor : '#ff4500'
    },

    tooltip: {
        trigger: 'axis',
        backgroundColor: 'rgba(0,0,0,1)',
        padding: [10, 15, 10, 15],
        axisPointer : {
            type : 'line',
            lineStyle : {
                color: '#008acd'
            },
            crossStyle: {
                color: '#008acd'
            },
            shadowStyle : {
                color: 'rgba(120,120,120,0.1)'
            }
        }
    },

    dataZoom: {
        dataBackgroundColor: 'rgba(120,120,120,0.1)',
        fillerColor: 'rgba(120,120,120,0.05)',
        handleColor: '#ccc'
    },

    grid: {
        borderColor: 'rgba(120,120,120,0.1)',
        x : 40,
        y : 60,
        x2: 40,
        y2: 60
    },

    categoryAxis: {
        axisLine: {
            lineStyle: {
                color: '#008acd'
            }
        },
        axisLabel: {
            textStyle: {
                color: '#ccc'
            }
        },
        splitLine: {
            lineStyle: {
                color: ['rgba(120,120,120,0.1)']
            }
        }
    },

    valueAxis: {
        axisLine: {
            lineStyle: {
                color: '#008acd'
            }
        },
        axisLabel: {
            textStyle: {
                color: '#ccc'
            }
        },
        splitArea : {
            show : true,
            areaStyle : {
                color: ['rgba(250,250,250,0)','rgba(200,200,200,0.05)']
            }
        },
        splitLine: {
            lineStyle: {
                color: ['rgba(120,120,120,0.1)']
            }
        }
    },

    polar : {
        name : {
            textStyle: {
                color: '#ccc'
            }
        },
        axisLine: {
            lineStyle: {
                color: 'rgba(120,120,120,0.1)'
            }
        },
        splitArea : {
            show : true,
            areaStyle : {
                color: ['rgba(250,250,250,0.2)','rgba(120,120,120,0.1)']
            }
        },
        splitLine : {
            lineStyle : {
                color : 'rgba(120,120,120,0.1)'
            }
        }
    },

    timeline : {
        lineStyle : {
            color : '#008acd'
        },
        controlStyle : {
            normal : { color : '#008acd'},
            emphasis : { color : '#008acd'}
        },
        symbol : 'emptyCircle',
        symbolSize : 3
    },

    bar: {
        itemStyle: {
            normal: {
                barBorderRadius: 5
            },
            emphasis: {
                barBorderRadius: 5
            }
        }
    },

    line: {
        smooth : true,
        symbol: 'emptyCircle',
        symbolSize: 3
    },
    
    k: {
        itemStyle: {
            normal: {
                color: '#d87a80',
                color0: '#2ec7c9',
                lineStyle: {
                    color: '#d87a80',
                    color0: '#2ec7c9'
                }
            }
        }
    },
    
    scatter: {
        symbol: 'circle',
        symbolSize: 4
    },

    radar : {
        symbol: 'emptyCircle',
        symbolSize:3
    },

    map: {
        itemStyle: {
            normal: {
                areaStyle: {
                    color: '#ddd'
                },
                label: {
                    textStyle: {
                        color: '#d87a80'
                    }
                }
            },
            emphasis: {
                areaStyle: {
                    color: '#fe994e'
                }
            }
        }
    },
    
    force : {
        itemStyle: {
            normal: {
                linkStyle : {
                    color : '#1e90ff'
                }
            }
        }
    },

    chord : {
        itemStyle : {
            normal : {
                borderWidth: 1,
                borderColor: 'rgba(128, 128, 128, 0.5)',
                chordStyle : {
                    lineStyle : {
                        color : 'rgba(128, 128, 128, 0.5)'
                    }
                }
            },
            emphasis : {
                borderWidth: 1,
                borderColor: 'rgba(128, 128, 128, 0.5)',
                chordStyle : {
                    lineStyle : {
                        color : 'rgba(128, 128, 128, 0.5)'
                    }
                }
            }
        }
    },

    gauge : {
        axisLine: {
            lineStyle: {
                color: [[0.2, '#2ec7c9'],[0.8, '#5ab1ef'],[1, '#d87a80']], 
                width: 10
            }
        },
        axisTick: {
            splitNumber: 10,
            length :15,
            lineStyle: {
                color: 'auto'
            }
        },
        splitLine: {
            length :22,
            lineStyle: {
                color: 'auto'
            }
        },
        pointer : {
            width : 5
        }
    },
    
    textStyle: {
        fontFamily: 'Arial, Verdana, sans-serif'
    }
};
