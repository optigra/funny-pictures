//Final Tiles Gallery 1.4 by GreenTreeLabs
//http://codecanyon.net/item/final-tiles-gallery/4734090
(function($) {
    $.finalTilesGallery = function(element, options) {
		function supportsTransitions() {
		
		    var b = document.body || document.documentElement;
		    var s = b.style;
		    var p = 'transition';
		    if(typeof s[p] == 'string') {return true; }
		
		    // Tests for vendor specific prop
		    v = ['Moz', 'Webkit', 'Khtml', 'O', 'ms'],
		    p = p.charAt(0).toUpperCase() + p.substr(1);
		    for(var i=0; i<v.length; i++) {
		      if(typeof s[v[i] + p] == 'string') { return true; }
		    }
		    return false;
		}

        var Slot = function (cell_size, width, height) {
            this.position = { x: 0, y: 0 };
            this.blocks = { h: Math.floor(width / cell_size), v: Math.floor(height / cell_size) };
            this.size = { width: this.blocks.h * cell_size, height: this.blocks.v * cell_size };
            this.px = { width: width, height: height };
            this.cell_size = cell_size;
        }

        Slot.prototype.resize = function (blocks) {
            //nw : nh = w : h => nh = nw * h / w
            var new_width = blocks * this.cell_size;
            var new_height = (new_width * this.px.height) / this.px.width;
            var bv = this.blocks.v;
            this.blocks.h = blocks;
            this.blocks.v = Math.floor(new_height / this.cell_size);
        }

        var Grid = function (cell_size, margin, min_tile_width, width) {        	
            this.slots = [];
            this.cells = [];
            this.margin = margin;
            this.min_tile_width = min_tile_width;
            this.cell_size = cell_size;
            this.width = width;
            this.hor_size = Math.floor(width / cell_size);            
            this.init();
        }

        Grid.prototype.init = function () {
            //TODO automatic vertical enlargment
            this.slots.length = 0;
            this.cells.length = 0;
            for (var i = 0; i < this.hor_size * 100; i++) {
                this.cells[i] = 0;
            }
        }

        Grid.prototype.add_slot = function (slot) {
            for (var j = 0; j < slot.blocks.v; j++) {
                for (var i = 0; i < slot.blocks.h; i++) {
                    var row = slot.position.y + j;
                    var column = slot.position.x + i;
                    //if (this.cells.length < row * this.hor_size + column)
                        
                    this.cells[row * this.hor_size + column] = 1;
                    this.cells.push(0);
                }
            }
            
            this.slots.push(slot);
        }

        Grid.prototype.count_free_cells_on_right = function (index) {
            var line = Math.floor(index / this.hor_size);
            var line_end = (line + 1) * this.hor_size;

            var length = 0;

            for (; index < line_end; index++, length++) {
                if (this.cells[index] == 1) {
                    break;
                }
            }

            return length;
        }

        Grid.prototype.insert = function (width, height) {
            var slot = new Slot(this.cell_size, width, height);
            var av_blocks = 0;
            var free_cell = 0;
            var line = 0;

            if (this.slots.length == 0) {
                av_blocks = this.hor_size;
                slot.position.x = 0;
                slot.position.y = 0;               
                //this.add_slot(slot);
            } else {

                //find first available cell
                var exit = false;
                for (; free_cell < this.cells.length; free_cell++) {
                    if (this.cells[free_cell] == 0) {

                        line = Math.floor(free_cell / this.hor_size);
                        var line_end = (line + 1) * this.hor_size;

                        //available blocks
                        av_blocks = 0;
                        for (var k = 0; k <= line_end - free_cell; k++) {
                            av_blocks = k;
                            if (this.cells[free_cell + k] == 1) {
                                //there's another slot on the right
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            //the slot need to be shrinked
            if (av_blocks < slot.blocks.h) {
                slot.resize(av_blocks);
            } else {
                var free_on_right = this.count_free_cells_on_right(free_cell + slot.blocks.h);                
                if (free_on_right * this.cell_size - plugin.settings.margin < this.min_tile_width) {
                    slot.resize(av_blocks);
                }
            }

            slot.position.x = free_cell % this.hor_size;
            slot.position.y = Math.floor(free_cell / this.hor_size);
            
            this.add_slot(slot);
            return slot;
        }
    
        //for debug purpouses
        Grid.prototype.print = function () {
            var log = "";
            for (var i = 1; i <= this.cells.length; i++) {
                log += this.cells[i - 1] == 1 ? "██" : "__";
                if (i > 0 && i % this.hor_size == 0)
                    log += "\n";
            }
            $("pre").text(log);
        }

        var defaults = {
            margin: 10,
            minTileWidth: 200,
            autoLoadURL: null,
            autoLoadOffset: 150,
            gridCellSize: 10,
            onComplete: function () { },
            onUpdate: function () { }
        }

        var plugin = this;
        var grid = null;
        var transitions = supportsTransitions();
        var firstAnimation = true;
        var isLoading = false;
        var currentPage = 1;

        plugin.settings = {}

        var $element = $(element),
             element = element;

        var currentWidth = $element.width();
        var completed = false;

		var setupFilters = function() {
			if($element.find(".ftg-filters").length == 0)
				return;				
				
			$element.find(".ftg-filters a").click(function(e) {
				e.preventDefault();
				
				if($(this).hasClass("selected"))
					return;
				
				$element.find(".ftg-filters .selected").add(this).toggleClass("selected");
				
				var ft = $(this).attr("href").substr(1);
				var $tiles = $("void");
				grid = null;
				if(ft.length > 0) {
					$element.find(".tile").addClass("ftg-hidden").hide().css({
						top:0,
						left: "50%",
						opacity: 0
					});
					$tiles = $element.find(".ftg-" + ft).show().removeClass("ftg-hidden");
				} else {
					$tiles = $element.find(".tile").removeClass("ftg-hidden").show();
				}
				entile($tiles);
			});
		}
		
		plugin.addElements = function (html) {
			var $hidden = $("<div />").hide();			
			$hidden.append(html);
			var $tiles = $hidden.find(".tile");
			styleTiles($tiles, { top: $(window).scrollTop() + 500 });
			$("body").append($hidden);
			
			$tiles.appendTo($element.find(".ftg-items"));
			$hidden.remove();
			preloadImages($tiles);
		}
		
		plugin.showLoading = function () {
		    var panel = $("<div class='ftg-loading-panel' />");
		    panel.append("<p>Loading</p>");
		    panel.hide();
		    $("body").append(panel);
		    panel.fadeIn();
		}

		plugin.hideLoading = function () {		    
		    $(".ftg-loading-panel").fadeOut("fast", function () {
		        $(this).remove();
		    });
		}

        plugin.init = function() {

            plugin.settings = $.extend({}, defaults, options);

            $element.find(".ftg-items").css({
                position: "relative",
                minWidth: plugin.settings.minTileWidth
            });
            var $tiles = $element.find(".tile");    
            styleTiles($tiles);
            setupFilters();            
            preloadImages($tiles);
            
            var resTo = 0;
            
            $(window).resize(function () {
                clearTimeout(resTo);
                if (currentWidth != $element.width()) {
                    resTo = setTimeout(function () {
                    	grid = null;
                    	entile($element.find(".tile"));
                    }, 200);
                }
            });
			var ajaxComplete = false;
            if (plugin.settings.autoLoadURL) {
                $(window).scroll(function () {
                    if (! ajaxComplete && ! isLoading && $(window).scrollTop() >= $(document).height() - $(window).height() - plugin.settings.autoLoadOffset) {
                        isLoading = true;
                        plugin.showLoading();
                        $.get(plugin.settings.autoLoadURL, { page: ++currentPage }, function (html) {
                            if($.trim(html).length == 0) {
	                            ajaxComplete = true;
                            } else {
	                        	plugin.addElements(html);    
                            }                            	                            
                        });
                    }
                });
            }
        }
        
        var styleTiles = function($tiles, styles) {
	        $tiles.css({
                position: "absolute",
                left: "50%"
            });
            
            if(styles) {            
	            for(var style in styles) {
		            $tiles.css(style, styles[style]);
	            }
            }
            
            $tiles.children().css({ position: "relative" });

//            var ie = /MSIE (\d+\.\d+);/.test(navigator.userAgent);
//            var opera = /Opera/.test(navigator.userAgent);

            $tiles.find(".item").css({
                width: "100%",                
                display: "none" // ie || opera ? "block" : "none"                
            });  
        }
        
        var preloadImages = function($tiles) {
	        var totalImages = 0;
            $tiles.each(function () {
                if($(this).data("type") == null || $(this).data("type") == "image")
                    totalImages++;
            });
            var loadedImages = 0;
            if(totalImages == 0) {
	            assignImagesSize($tiles);
                entile($tiles);
            }            

            $tiles.each(function () {
                if($(this).data("type") == null || $(this).data("type") == "image") {
                    var image = $(this).find(".item");
                    //TODO check integer values
                    if(image.attr("width") && image.attr("height")) {
	                 	if (++loadedImages == totalImages) {
                            assignImagesSize($tiles);
                            entile($tiles);
                        }
                    }
                    var img = new Image();
                    img.onload = function () {                    	
                        if (++loadedImages == totalImages) {
                            assignImagesSize($tiles);
                            entile($tiles);
                        }
                    };
                    img.onerror = function () {
                        if (++loadedImages == totalImages) {
                            assignImagesSize($tiles);
                            entile($tiles);
                        }
                    };
                    img.src = image.attr("src");
                }
            });
        }
        
        var assignImagesSize = function ($tiles) {
            $tiles.each(function () {
                var size = {};
                var $item = $(this).find(".item");
                if($(this).data("type") == null || $(this).data("type") == "image") {
                    if($item.attr("width")) {
						size = {
							width: parseInt($item.attr("width")),
	                        height: parseInt($item.attr("height"))
						}	                    
                    } else {
                    $item.show();
	                    size = {
	    	                width: $item.width(),
	                        height: $item.height()
	                    };
                    }
                    $item.hide();
                }
                if($(this).data("type") == "video") {
                    size = {
                        width: parseInt($(this).find("iframe").attr("width")),
                        height: parseInt($(this).find("iframe").attr("height")),
                    }
                }
                
                if(size.width < plugin.settings.minTileWidth) {
                	//w : h = w1 : h1 => h1 = h * w1 / w
	                size.height = size.height * plugin.settings.minTileWidth / size.width;
	                size.width = plugin.settings.minTileWidth;
                }
                $item.data("size", size);
            });
        }
		
		
        var entile = function ($tiles) {
        	if(! grid)
	            grid = new Grid(plugin.settings.gridCellSize, plugin.settings.margin, plugin.settings.minTileWidth, $element.width());
            var maxHeight = 0;
            
            var transition = firstAnimation ? "none" : "all .5s";
            //var $tiles = $element.find(".tile");
            $tiles.css({
	            transition: transition
            });
            $tiles.find(".item").css({
                display: "block" 
            });
            firstAnimation = false;
            $tiles.each(function () {
            	if($(this).hasClass("ftg-hidden"))
            		return;
            	
            	$(this).show();
                var size = $(this).find(".item").data("size");

                var slot = grid.insert(size.width + plugin.settings.margin, size.height + plugin.settings.margin);
                
                $(this).find(".caption").css({
	                width: "100%"
                });

                var top = slot.cell_size * slot.position.y;
                var height = slot.cell_size * slot.blocks.v;
                if (top + height > maxHeight)
                    maxHeight = top + height + plugin.settings.margin;                
				
				var tileWidth = slot.cell_size * slot.blocks.h - plugin.settings.margin;
				var tileHeight = slot.cell_size * slot.blocks.v - plugin.settings.margin;
				
				if(supportsTransitions()) {
                    $(this).css({
	                    top: top,
	                    left: slot.cell_size * slot.position.x,
	                    width: slot.cell_size * slot.blocks.h,
	                    height: height,
	                    opacity: 1
	                }, 500);
	                $(this).children().css({
	                    display: "block",
	                    overflow: "hidden"               
	                }).css({
	                    width: tileWidth,
	                    height: tileHeight
	                });
                } else {
	                $(this).animate({
	                    top: top,
	                    left: slot.cell_size * slot.position.x,
	                    width: slot.cell_size * slot.blocks.h,
	                    height: height,
	                    opacity: 1
	                }, 500);
	                $(this).children().css({
	                    display: "block",
	                    overflow: "hidden"
	                }).animate({
	                    width: tileWidth,
	                    height: tileHeight
	                });
                }

				$element.find(".ftg-items").height(maxHeight);                
            });
            $tiles.find("img.item").each(function (i, item) {
                var $item = $(item);
                var size = $item.data("size");
                
                $item.css({
            		marginTop: 0,
            		marginLeft: 0,
            		width: "100%",
            		maxWidth: "9999em"
                });

                var ratioImg = size.width / size.height;
                var ratioTile = $item.parent().width() / $item.parent().height();
                
                if (ratioImg == ratioTile) {
                    $item.css({
                        width: $item.parent().width(),
                        height: $item.parent().height()
                    });
                    $item.attr("case", "0");
                    return;//continue $.each loop
                }
                
                if (ratioImg >= ratioTile) {
                    $item.css({
                        width: "auto",
                        height: $item.parent().height()
                    });
                    
                    $item.attr("case", "2");

                    var diff = $item.width() - $item.parent().width();
                    $item.css("margin-left", diff / -2); 
                } else {
                    
                    //ratioImg < ratioTile                    
                    $item.css({
                        width: $item.parent().width(),
                        height: "auto"
                    });
                    
                    $item.attr("case", "4");

                    var diff = $item.height() - $item.parent().height();
                    $item.css("margin-top", diff / -2);                    
                }

            });
            //grid.print();

            currentWidth = $element.width();
            if (!completed) {
                completed = true;
                plugin.settings.onComplete.call(plugin);
            } else {
                plugin.settings.onUpdate.call(plugin);
            }

            isLoading = false;
            plugin.hideLoading();
        }

        plugin.init();
    }

    $.fn.finalTilesGallery = function(options) {
        return this.each(function() {
            if (undefined == $(this).data('finalTilesGallery')) {
                var plugin = new $.finalTilesGallery(this, options);
                $(this).data('finalTilesGallery', plugin);
            }
        });
    }
})(jQuery);