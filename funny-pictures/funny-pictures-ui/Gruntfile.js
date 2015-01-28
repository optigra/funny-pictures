'use strict';

module.exports = function(grunt) {
	require('time-grunt')(grunt);
	require('jit-grunt')(grunt);
	grunt
			.initConfig({
				yeoman : {
					source : 'src/main/sourceapp',
					dist : 'src/main/webapp'
				},
				watch : {
					sass : {
						files : [ '<%= yeoman.source %>/scss/**/*.{scss,sass}' ],
						tasks : [ 'sass:server', 'autoprefixer:dist' ]
					},
					scripts : {
						files : [ '<%= yeoman.source %>/js/**/*.{js}' ],
						tasks : [ 'uglify:dist' ]
					}
				},
				clean : {
					dist : [ '.tmp', '<%= yeoman.dist %>/css',
					//					         '<%= yeoman.dist %>/js'
					]

				},
				copy : {
					css : {
						files : [ {
							expand: true,
						    cwd: 'bower_components/angular-material/',
						    src: 'angular-material.css',
						    dest: '.tmp/scss/',
							rename : function(dest, src) {
								return dest + "_"
										+ src.substring(0, src.indexOf('.'))
										+ '.scss';
							}
						} ]
					}
				},
				sass : {
					options : {
						includePaths : [
								'bower_components/bootstrap-sass/assets/stylesheets',
								'.tmp/scss' 
								]
					},
					server : {
						options : {
							sourceMap : true
						},
						files : [ {
							expand : true,
							cwd : '<%= yeoman.source %>/scss',
							src : '**/*.{scss,sass}',
							dest : '.tmp/css',
							ext : '.css'
						} ]
					},
					dist : {
						options : {
							outputStyle : 'compressed'
						},
						files : [ {
							expand : true,
							cwd : '<%= yeoman.source %>/scss',
							src : '**/*.{scss,sass}',
							dest : '.tmp/css',
							ext : '.css'
						} ]
					}
				},
				autoprefixer : {
					options : {
						browsers : [ 'last 3 versions' ]
					},
					dist : {
						files : [ {
							expand : true,
							cwd : '.tmp/css',
							src : '**/*.css',
							dest : '<%= yeoman.dist %>/css'
						} ]
					}
				},
				uglify : {
					options : {
						preserveComments : false
					},
					dist : {
						files : {
							'<%= yeoman.dist %>/js/scripts.js' : [
//									'bower_components/hammerjs/hammer.js',
									'bower_components/angular/angular.js',
									'bower_components/angular-resource/angular-resource.js',
									'bower_components/angular-route/angular-route.js',
									'bower_components/angular-bootstrap/ui-bootstrap-tpls.js',

//									'bower_components/angular-animate/angular-animate.js',
//									'bower_components/angular-aria/angular-aria.js',
//									'bower_components/angular-material/angular-material.js',

							//									'bower_components/jquery/dist/jquery.js',
							//									'<%= yeoman.source %>/js/app.js',
							//									'<%= yeoman.source %>/js/controllers.js'
							]
						}
					}
				},
			});

	grunt.registerTask('serve', [ 'clean', 'copy:css', 'sass:server',
			'autoprefixer', 'uglify', 'watch' ]);
	grunt.registerTask('build', [ 'clean', 'copy:css', 'sass:dist',
			'autoprefixer', 'uglify' ]);

	grunt.registerTask('default', [ 'build' ]);
};