'use strict';

module.exports = function(grunt) {
    require('time-grunt')(grunt);
    require('jit-grunt')(grunt);
    grunt
        .initConfig({
            app: {
                source: 'src/main/sourceapp',
                dist: 'src/main/webapp'
            },
            watch: {
                sass: {
                    files: ['<%= app.source %>/scss/**/*.{scss,sass}'],
                    tasks: ['sass:server', 'autoprefixer:dist']
                },
                scripts: {
                    files: ['<%= app.source %>/js/**/*.{js}'],
                    tasks: ['uglify:dist']
                }
            },
            clean: {
                dist: ['.tmp', '<%= app.dist %>/css', '<%= app.dist %>/js']

            },
            copy: {
                css: {
                    files: [{
                        expand: true,
                        cwd: 'bower_components/angular-material/',
                        src: 'angular-material.css',
                        dest: '.tmp/scss/',
                        rename: function(dest, src) {
                            return dest + "_" + src.substring(0, src.indexOf('.')) + '.scss';
                        }
                    }]
                }
            },
            sass: {
                options: {
                    includePaths: [
                        'bower_components/bootstrap-sass/assets/stylesheets',
                        '.tmp/scss'
                    ]
                },
                server: {
                    options: {
                        sourceMap: true
                    },
                    files: [{
                        expand: true,
                        cwd: '<%= app.source %>/scss',
                        src: '**/*.{scss,sass}',
                        dest: '.tmp/css',
                        ext: '.css'
                    }]
                },
                dist: {
                    options: {
                        outputStyle: 'compressed'
                    },
                    files: [{
                        expand: true,
                        cwd: '<%= app.source %>/scss',
                        src: '**/*.{scss,sass}',
                        dest: '.tmp/css',
                        ext: '.css'
                    }]
                }
            },
            autoprefixer: {
                options: {
                    browsers: ['last 3 versions']
                },
                dist: {
                    files: [{
                        expand: true,
                        cwd: '.tmp/css',
                        src: '**/*.css',
                        dest: '<%= app.dist %>/css'
                    }]
                }
            },
            uglify: {
                server: {
                    options: {
                        mangle: false,
                        beautify: true
                    },
                    files: {
                        '<%= app.dist %>/js/scripts.js': [
                            'bower_components/jquery/dist/jquery.js',

                            'bower_components/masonry/dist/masonry.pkgd.js',
                            'bower_components/imagesloaded/imagesloaded.pkgd.js',

                            'bower_components/hammerjs/hammer.js',
                            'bower_components/angular/angular.js',
                            'bower_components/angular-resource/angular-resource.js',
                            'bower_components/angular-route/angular-route.js',
                            'bower_components/angular-animate/angular-animate.js',
                            'bower_components/angular-aria/angular-aria.js',
                            'bower_components/angular-material/angular-material.js',
                            'bower_components/angular-masonry/angular-masonry.js',
                            'bower_components/angular-translate/angular-translate.js',
                            '<%= app.source %>/js/vendor/ui-bootstrap-custom-tpls-0.12.0.js'
                        ],

                        '<%= app.dist %>/js/app.js': [
                            '<%= app.source %>/js/app.js',
                            '<%= app.source %>/js/properties/development.js'
                        ],

                        '<%= app.dist %>/js/controllers.js': [
                            '<%= app.source %>/js/controllers.js',
                            '<%= app.source %>/js/i18n.js'
                        ]
                    }
                },
                dist: {
                    options: {
                        mangle: false,
                        preserveComments: false,
                        report: 'min'
                    },
                    files: {
                        '<%= app.dist %>/js/scripts.js': [
                            'bower_components/jquery/dist/jquery.js',

                            'bower_components/masonry/dist/masonry.pkgd.js',
                            'bower_components/imagesloaded/imagesloaded.pkgd.js',

                            'bower_components/hammerjs/hammer.js',
                            'bower_components/angular/angular.js',
                            'bower_components/angular-resource/angular-resource.js',
                            'bower_components/angular-route/angular-route.js',
                            'bower_components/angular-animate/angular-animate.js',
                            'bower_components/angular-aria/angular-aria.js',
                            'bower_components/angular-material/angular-material.js',
                            'bower_components/angular-masonry/angular-masonry.js',
                            '<%= app.source %>/js/vendor/ui-bootstrap-custom-tpls-0.12.0.js'
                        ],
                        '<%= app.dist %>/js/app.js': [
                            '<%= app.source %>/js/app.js',
                            '<%= app.source %>/js/properties/production.js'
                        ],
                        '<%= app.dist %>/js/controllers.js': [
                            '<%= app.source %>/js/controllers.js'
                        ]
                    }
                }
            }
        });


    grunt.registerTask('serve', ['clean', 'copy:css', 'sass:server',
        'autoprefixer', 'uglify:server'
    ]);
    grunt.registerTask('build', ['clean', 'copy:css', 'sass:dist',
        'autoprefixer', 'uglify:dist'
    ]);

    grunt.registerTask('default', ['build']);
};
