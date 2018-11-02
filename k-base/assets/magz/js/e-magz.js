$(function(){
	var youtube_api_key = 'AIzaSyADrrUFFLxuICKBewoVisQO4Ws9Op8xTv0';

	var loading = {
		show: function() {
			$("body").append("<div class='main-loading'></div>");
		},
		hide: function() {
			$(".main-loading").remove();
		}
	}

	var backdrop = {
		show: function(el) {
			if(!el) el = 'body';
			$(el).prepend($("<div/>", {
				class: "backdrop"
			}));
			$(".backdrop").fadeIn();
		},
		hide: function() {
			$(".backdrop").fadeOut(function() {
				$(".backdrop").remove();
			});
		},
		click: function(clicked) {
			$(document).on("click", ".backdrop", function() {
				clicked.call(this);
				return false;
			});
		}
	}

	var sectionFirstPadding = function() {
		if($("header.primary").length) {
			$("section").eq(0).addClass("first");
			$("section.first").css({
				paddingTop: $("header.primary").outerHeight() + 15
			})			
		}
		$(window).on("resize",function(){
			if($("header.primary").length) {
				$("section.first").css({
					paddingTop: $("header.primary").outerHeight() + 15
				})
			}
		});
	}

	var stickyHeader = function() {	
		var didScroll;
		$(window).on("scroll", function(event){
			didScroll = true;
		});

		setInterval(function() {
			if(didScroll) {
				hasScrolled();
				didScroll = false;
			}
		},250);

		var hasScrolled = function() {
			var scrollTop = $(this).scrollTop();
			var toTop = 0;
			//alert(scrollTop);
			$("header.primary > :not(.menu)").each(function(){
				toTop += $(this).outerHeight();
			});

			if(scrollTop > 100) {
				$("header.primary").addClass("up").css({
					top: -toTop
				});
			}
			if(scrollTop < 300) {
				$("header.primary").removeClass("up").css({
					top: 0
				});
			}
		}
	}

	// love
	var love = function() {	
		$(".love").each(function(){
			$(this).find("div").html($.number($(this).find("div").html()));
			$(this).click(function(){
				var countNow = $(this).find("div").html().replace(',', '');
				if(!$(this).hasClass("active")) {
					$(this).find(".animated").remove();
					$(this).addClass("active");
					$(this).find("i").removeClass("ion-android-favorite-outline");
					$(this).find("i").addClass("ion-android-favorite");
					$(this).find("div").html(parseInt(countNow) + 1);
					$(this).find("div").html($.number($(this).find("div").html()));
					$(this).append($(this).find("i").clone().addClass("animated"));
					$(this).find("i.animated").on("animationend webkitAnimationEnd oAnimationEnd MSAnimationEnd", function(e){
						$(this).remove();
					  $(this).off(e);
					});
					// add some code ("love")
				}else{
					$(this).find(".animated").remove();
					$(this).removeClass("active");
					$(this).find("i").addClass("ion-android-favorite-outline");
					$(this).find("i").removeClass("ion-android-favorite");
					$(this).find("div").html(parseInt(countNow) - 1);
					$(this).find("div").html($.number($(this).find("div").html()));

					// add some code ("unlove")
				}
				return false;
			});
		});
	}


	// newsletter
	var newsletter = function() {
		$(".newsletter").submit(function(){
			var $this = $(this),
			newsletter = {
				start: function() {
					$this.find(".icon").addClass("spin");
					$this.find(".icon i").removeClass("ion-ios-email-outline");
					$this.find(".icon i").addClass("ion-load-b");
					$this.find(".icon h1").html("Please wait ...");
					$this.find(".btn").attr("disabled", true);
					$this.find(".email").attr("disabled", true);
				},
				end: function() {
					$this.find(".icon").removeClass("spin");
					$this.find(".icon").addClass("success");
					$this.find(".icon i").addClass("ion-checkmark");
					$this.find(".icon i").removeClass("ion-load-b");
					$this.find(".icon h1").html("Thank you!");
					$this.find(".email").val("");				
					$this.find(".btn").attr("disabled", false);
					$this.find(".email").attr("disabled", false);
					$.toast({
						text: "Thanks for subscribing!",
						position: 'bottom-right',
						bgcolor: '#E01A31',
						icon: 'success',
						heading: 'Newsletter',
						loader: false
					});
				},
				error: function() {
					$this.find(".icon").removeClass("spin");
					$this.find(".icon").addClass("error");
					$this.find(".icon i").addClass("ion-ios-close-outline");
					$this.find(".icon i").removeClass("ion-load-b");
					$this.find(".icon h1").html("Failed, try again!");
					$this.find(".btn").attr("disabled", false);
					$this.find(".email").attr("disabled", false);
					$.toast({
						text: "Failed, network error. Please try again!",
						position: 'bottom-right',
						icon: 'error',
						heading: 'Newsletter',
						loader: false
					});
				}
			}

			if($this.find(".email").val().trim().length < 1) {
				$this.find(".email").focus();
			}else{
				/* 
				 * Add your ajax code
				 * ------------------
				 * For example:
				 * $.ajax({
				 * 		url: "subscribe_url",
				 * 		type: "post",
				 *  	data: $this.serialize(),
				 * 		error: function() {
				 * 			newsletter.error();
				 * 		},	
				 * 		beforeSend: function() {
				 * 			newsletter.start();
				 * 		},	
				 * 		success: function() {
				 * 			newsletter.end();
				 * 		}
				 * });
				 });
				*/

				newsletter.start();

				setTimeout(function(){
					newsletter.end();
				}, 2000);
			}

			return false;
		});		
	}

	var featuredImage = function() {	
	  $("#featured figure img").each(function(){
	  	$(this).parent().css({
	  		backgroundImage: 'url('+$(this).attr('src')+')',
	  		backgroundSize: 'cover',
	  		backgroundRepeat: 'no-repeat',
	  		backgroundPosition: 'center'
	  	});
	  	$(this).remove();
	  });
	}

	var headline = function() {
	  var headlineCarousel = $("#headline").owlCarousel({
	  	items: 1,
	  	dots: false,
	  	// autoplay: true,
	  	autoplayTimeout: 3000,
	  	loop: true
	  });

		$("#headline-nav [data-slide=next]").click(function(){
			headlineCarousel.trigger('next.owl.carousel');
		});

		$("#headline-nav [data-slide=prev]").click(function(){
			headlineCarousel.trigger('prev.owl.carousel');
		});		
	}

  // floating label
  var floatingLabel = function() {
	  $(".floating.focus").each(function(){
	  	$(this).find(".form-control").focus(function(){
	  		$(this).parent().addClass("focused");
	  	}).on("blur", function(){
	  		if($(this).val().trim().length < 1) {
		  		$(this).parent().removeClass("focused");
	  		}
	  	});
	  });  	
  }

  // browser
	if($.browser.safari) {
		$("head").append($("<link/>", {
			rel: "stylesheet",
			href: "css/safari.css"
		}));
	}else if($.browser.mozilla) {
		$(".social li").each(function() {
			$(this).find("rect").attr("width", "100%");
			$(this).find("rect").attr("height", "100%");
		});
	}

	var bestOfTheWeek = function() {	
		var botwCarousel = $(".carousel-1").owlCarousel({
			items: 4,
			itemElement: 'article',
			margin: 20,
			nav: false,
			dots: false,
			responsive: {
				1024: {
					items: 4
				},
				768: {
					items: 2
				},
				0: {
					items: 1
				}
			}
		});

		$("#best-of-the-week-nav .next").click(function(){
			botwCarousel.trigger('next.owl.carousel');
		});

		$("#best-of-the-week-nav .prev").click(function(){
			botwCarousel.trigger('prev.owl.carousel');
		});
	}

	var youtubeAPI = function() {
		$("[data-youtube]").each(function(vl_i){
			var $this = $(this),
					$options = $this.data("youtube"),
					$options = JSON.parse("{"+$options+"}"),
					options = {
						items: 1,
						dots: false,
					};

			if($options.autoplay == true) {
				options['autoplay'] = true;
			}

			var $id = "";
			$this.find('[data-youtube-id]').each(function(){
				var $item = $(this),
						$itemId = $item.data('youtube-id');
				$id += $itemId+',';
			});
			$id = $id.substr(0, $id.length-1);

			$.ajax({
				url: 'https://www.googleapis.com/youtube/v3/videos?key='+youtube_api_key+'&part=snippet,contentDetails,statistics,status&id=' + $id,
				beforeSend: function() {
					var	$element = '<figure><div class="duration">0:00</div><div class="play"><i class="ion-play"></i></div></figure>';
							$element += '	 <div class="desc">';
							$element +=	'  <h2 class="title loading"></h2>';
							$element +=	'  <div class="author loading"></div>';
							$element += '</div>';
					$this.find("[data-youtube-id]").each(function(i){
						$(this).append($element);
					});
				},
				complete: function() {
				},
				success: function(data) {
					$this.find("[data-youtube-id]").each(function(i){
						var $item = $(this);
						$item.find(".duration").html(convert_time(data.items[i].contentDetails.duration));
						$item.find("figure").removeClass("loading").append("<img src='"+data.items[i].snippet.thumbnails.medium.url+"'>");
						$item.find(".title").removeClass("loading").html(data.items[i].snippet.title);
						$item.find(".author").removeClass("loading").html(data.items[i].snippet.channelTitle);
						if($item.data("action") == 'new_tab') {
							$item.attr('href', 'https://youtube.com/watch?v=' + data.items[i].id).attr('target', '_blank');
						}else if($item.data("action") == 'magnific') {
							$item.attr('href', 'https://youtube.com/watch?v=' + data.items[i].id);
							$item.magnificPopup({
								type: 'iframe',
								iframe: {
									markup: '<div class="mfp-iframe-scaler">'+
												'<div class="mfp-close"></div>'+
												'<iframe class="mfp-iframe" frameborder="0" allowfullscreen></iframe>'+
											'</div>',
									patterns: {
										youtube: {
											index: 'youtube.com/',
											id: 'v=',
											src: '//www.youtube.com/embed/%id%?autoplay=1'
										}
									},
									srcAction: 'iframe_src', // Templating object key. First part defines CSS selector, second attribute. "iframe_src" means: find "iframe" and set attribute "src".
								}
							})
						}
					});
				}
			});

			if($options.carousel == true) {
				$this.addClass('owl-carousel owl-theme');
				var video_list = $this.owlCarousel(options);
				$($options.nav).find(".prev").click(function(){
					video_list.trigger('prev.owl.carousel');
				});
				$($options.nav).find(".next").click(function(){
					video_list.trigger('next.owl.carousel');
				});
			}
		});		
	}

	function convert_time(duration) {
    var a = duration.match(/\d+/g);
    if (duration.indexOf('M') >= 0 && duration.indexOf('H') == -1 && duration.indexOf('S') == -1) {
      a = [0, a[0], 0];
    }
    if (duration.indexOf('H') >= 0 && duration.indexOf('M') == -1) {
      a = [a[0], 0, a[1]];
    }
    if (duration.indexOf('H') >= 0 && duration.indexOf('M') == -1 && duration.indexOf('S') == -1) {
      a = [a[0], 0, 0];
    }
    duration = 0;
    if (a.length == 3) {
      duration = duration + parseInt(a[0]) * 3600;
      duration = duration + parseInt(a[1]) * 60;
      duration = duration + parseInt(a[2]);
    }
    if (a.length == 2) {
      duration = duration + parseInt(a[0]) * 60;
      duration = duration + parseInt(a[1]);
    }
    if (a.length == 1) {
      duration = duration + parseInt(a[0]);
    }
    var h = Math.floor(duration / 3600);
    var m = Math.floor(duration % 3600 / 60);
    var s = Math.floor(duration % 3600 % 60);
    return ((h > 0 ? h + ":" + (m < 10 ? "0" : "") : "") + m + ":" + (s < 10 ? "0" : "") + s);
	}

	var verticalSlider = function () {
		$(".vertical-slider").each(function(ii){
			var $this = $(this), 
					$item = $this.find($this.data("item")),
					$item_height = 0,
					$item_max = $this.data("max"),
					$nav = $($this.data("nav"));

			$this.attr("data-current", 1);

			$item.each(function(i){
				i++;
				$(this).attr("data-list", i);
				if(i > $item_max) {
					return;
				}
				$item_height += ($(this).outerHeight() + 15);
			});

			$this.css({
				overflow: 'hidden'
			});
			$item.wrapAll($("<div/>", {
				style: 'height:'+$item_height+'px;',
				id: 'vs_inner_'+ii
			}))

			function vs_next() {
				var $current = $this.attr("data-current"),
						$next = $current;

				var $item_move = $this.find("#vs_inner_"+ii+' '+$this.data("item")+"[data-list="+$next+"]");

				$item_move.fadeOut(function(){
					var $clone = $item_move.clone().fadeIn();
					$item_move.remove();
					$this.find("#vs_inner_"+ii).append($clone);
				});

				$next = parseInt($current) + 1;
				if($next > $item.length) {
					$next = 1;
				}
				$this.attr('data-current', $next);
			}

			function vs_prev() {
				var $current = $this.attr("data-current"),
						$next = $current;

				$next = parseInt($current) - 1;
				if($next < 1) {
					$next = $item.length;
				}
				$this.attr('data-current', $next);

				var $item_move = $this.find("#vs_inner_"+ii+' '+$this.data("item")+"[data-list="+$next+"]");
				var $clone = $item_move.clone().css('display','none');
				$item_move.remove();
				$this.find("#vs_inner_"+ii).prepend($clone.fadeIn());
			}

			$nav.find(".prev").click(function(){
				vs_prev();
			});
			$nav.find(".next").click(function(){
				vs_next();
			});
			setInterval(function(){
				vs_next();
			},10000);
		});		
	}

	var featured = function() {
		$("#featured").owlCarousel({
			items: 1,
			dots: false,
			// autoplay: true,
			loop: true
		});		
	}

	var magnificGallery = function() {
		$('[data-magnific="gallery"]').each(function(){
			var $this = $(this);

			$this.magnificPopup({
				type: 'image',
				delegate: 'a',
				gallery: {
					enabled: true
				},
				preloader: true,
	  		})
		});		
	}

	// ease scroll
	var easeScrollFunc = function() {
		$("html").easeScroll();
	}

	var toggleMobile = function() {
		$(document).on("click", "[data-toggle=menu]", function() {
			var $this = $(this),
					$target = $($this.data("target"));

			backdrop.click(function() {
				$(".nav-list").removeClass("active");
				$(".nav-list .dropdown-menu").removeClass("active");
				$(".nav-title a").text("Menu");
				$(".nav-title .back").remove();
				$("body").css({
					overflow: "auto"
				});
				backdrop.hide();
			});

			$("body").css({
				overflow: "hidden"
			});
			backdrop.show('#menu-list');
			setTimeout(function() {
				$target.find('.nav-list').addClass("active");
			},50);
			return false;
		});

		$(document).on("click", ".nav-list li.magz-dropdown > a", function() {
			var $this = $(this),
					$parent = $this.parent(),
					$titleBefore = $this.text(),
					$back = '<div class="back"><i class="ion-ios-arrow-left"></i></div>';

			if($(".nav-title .back").length) {
				var titleNow = $(".nav-title .back").attr('data-title');
				titleNow += ("," + $this.text());
				$(".nav-title .back").attr('data-title', titleNow);
			}else{
				$(".nav-title").prepend($($back).attr('data-title', $(".nav-title a").text() + "," + $this.text()));
			}
			$(".nav-title a").html($this.text());
			$parent.find("> .dropdown-menu").fadeIn(100).addClass("active");
			return false;
		});

		var titleLen = 0;
		$(document).on("click", ".nav-title .back", function() {
			var $dd = $(".dropdown-menu.active"),
					$len = $dd.length,
					title;

			$dd.eq($len-1).removeClass("active");
			setTimeout(function() {
				$dd.eq($len-1).hide();
			},500);
			title = $(this).attr('data-title').split(",");
			titleLen = title.length-1;
			title = title.splice(0, titleLen);
			$(".nav-title a").text(title[title.length-1]);
			$(".nav-title .back").attr('data-title', title);
			if((title.length-1) == 0) {
				$(".nav-title .back").remove();
			}
			return false;
		});

		if(!$("#sidebar").length) {
			$("[data-toggle=sidebar]").hide();
		}
		$(document).on("click", "[data-toggle=sidebar]", function() {
			var $this = $(this),
					$target = $($this.data("target"));

			backdrop.click(function() {
				backdrop.hide();
				$target.removeClass("active");
				$("body").css({
					overflow: "auto"
				});
			});

			$("body").css({
				overflow: "hidden"
			});
			backdrop.show();
			setTimeout(function() {
				$target.addClass("active");
			},50);
			return false;
		});
	}

	var showPassword = function() {
		$("input[type='password']").each(function(i) {
			var $this = $(this);

			$this.wrap($("<div/>", {
				style: 'position:relative'
			}));
			$this.css({
				paddingRight: 60
			});
			$this.after($("<div/>", {
				html: 'Show',
				class: 'btn btn-primary btn-sm',
				id: 'passeye-toggle-'+i,
				style: 'position:absolute;right:10px;top:50%;transform:translate(0,-50%);-webkit-transform:translate(0,-50%);-o-transform:translate(0,-50%);padding: 2px 7px;font-size:12px;cursor:pointer;'
			}));
			$this.after($("<input/>", {
				type: 'hidden',
				id: 'passeye-' + i
			}));
			$this.on("keyup paste", function() {
				$("#passeye-"+i).val($(this).val());
			});
			$("#passeye-toggle-"+i).on("click", function() {
				if($this.hasClass("show")) {
					$this.attr('type', 'password');
					$this.removeClass("show");
					$(this).removeClass("btn-magz");
					$(this).addClass("btn-primary");
				}else{
					$this.attr('type', 'text');
					$this.val($("#passeye-"+i).val());				
					$this.addClass("show");
					$(this).removeClass("btn-primary");
					$(this).addClass("btn-magz");
				}
			});
		});
	}

	var sendContactForm = function() {
		$("#contact-form").submit(function() {
			var $this = $(this);
			$.ajax({
				url: 'server/send.php',
				type: "post",
				data: $this.serialize(),
				dataType: 'json',
				beforeSend: function() {
					loading.show();
				},
				complete: function() {
					loading.hide();
				},
				success: function(data) {
					if(data.status == true) {
						swal("Success", data.data, "success");
						$this[0].reset();
					}else{
						swal("Failed", data.data, "error");
					}
				}
			});
			return false;
		});
	}

	var loadFile = function() {
		$("[data-load]").each(function() {
			var $this = $(this);

			$.ajax({
				url: $this.attr('data-load'),
				beforeSend: function() {
					$this.html('Loading data ...');
				},
				error: function(xhr) {
					$this.html("[ERROR] Status: " + xhr.status + "\nResponse Text:\n " + xhr.responseText);
				},
				success: function(data) {
					$this.html(data);
				}
			})
		});
	}

	// Run Function
	sectionFirstPadding();

	stickyHeader();

	love();

	newsletter();

	featuredImage();

	headline();

	floatingLabel();

	bestOfTheWeek();

	youtubeAPI();

	verticalSlider();

	featured();

	magnificGallery();

	easeScrollFunc();

	toggleMobile();

	showPassword();

	sendContactForm();

	loadFile();
});