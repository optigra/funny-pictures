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
                            '<%= app.source %>/js/app.module.js',

                            '<%= app.source %>/js/core/app.core.js',
                            '<%= app.source %>/js/core/core.route.js',
                            '<%= app.source %>/js/core/core.values.js',
                            '<%= app.source %>/js/core/properties/development.constants.js',

                            '<%= app.source %>/js/directives/click-link.directive.js',
                            '<%= app.source %>/js/directives/click-selector.directive.js',
                            '<%= app.source %>/js/directives/file-model.directive.js',
                            '<%= app.source %>/js/directives/footer.directive.js',

                            '<%= app.source %>/js/header/header.directive.js',
                            '<%= app.source %>/js/header/header.controller.js',

                            '<%= app.source %>/js/funnies/funnies.factory.js',
                            '<%= app.source %>/js/funnies/funny-thumbnails.factory.js',
                            '<%= app.source %>/js/funnies/funny-thumbnails-by-picture.factory.js',

                            '<%= app.source %>/js/funnies/funnies.controller.js',
                            '<%= app.source %>/js/funnies/create-funny/create-funny.controller.js',
                            '<%= app.source %>/js/funnies/preview-funny/preview-funny.controller.js',

                            '<%= app.source %>/js/pictures/pictures.factory.js',
                            '<%= app.source %>/js/pictures/picture-thumbnails.factory.js',

                            '<%= app.source %>/js/pictures/create-picture/file-upload.service.js',

                            '<%= app.source %>/js/pictures/pictures.controller.js',
                            '<%= app.source %>/js/pictures/create-picture/create-picture.controller.js',

                            '<%= app.source %>/js/contact/feedbacks.factory.js',
                            '<%= app.source %>/js/contact/contact.controller.js',

                            '<%= app.source %>/js/i18n.js'
                        ]
                    }
                },
                dist: {
                    options: {
                        compress: true,
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
                            'bower_components/angular-translate/angular-translate.js',
                            '<%= app.source %>/js/vendor/ui-bootstrap-custom-tpls-0.12.0.js'
                        ],
                        
                        '<%= app.dist %>/js/app.js': [
                            '<%= app.source %>/js/app.module.js',

                            '<%= app.source %>/js/core/app.core.js',
                            '<%= app.source %>/js/core/core.route.js',
                            '<%= app.source %>/js/core/core.values.js',
                            '<%= app.source %>/js/core/properties/production.constants.js',

                            '<%= app.source %>/js/directives/click-link.directive.js',
                            '<%= app.source %>/js/directives/click-selector.directive.js',
                            '<%= app.source %>/js/directives/file-model.directive.js',
                            '<%= app.source %>/js/directives/footer.directive.js',

                            '<%= app.source %>/js/header/header.directive.js',
                            '<%= app.source %>/js/header/header.controller.js',

                            '<%= app.source %>/js/funnies/funnies.factory.js',
                            '<%= app.source %>/js/funnies/funny-thumbnails.factory.js',
                            '<%= app.source %>/js/funnies/funny-thumbnails-by-picture.factory.js',

                            '<%= app.source %>/js/funnies/funnies.controller.js',
                            '<%= app.source %>/js/funnies/create-funny/create-funny.controller.js',
                            '<%= app.source %>/js/funnies/preview-funny/preview-funny.controller.js',

                            '<%= app.source %>/js/pictures/pictures.factory.js',
                            '<%= app.source %>/js/pictures/picture-thumbnails.factory.js',

                            '<%= app.source %>/js/pictures/create-picture/file-upload.service.js',

                            '<%= app.source %>/js/pictures/pictures.controller.js',
                            '<%= app.source %>/js/pictures/create-picture/create-picture.controller.js',

                            '<%= app.source %>/js/contact/feedbacks.factory.js',
                            '<%= app.source %>/js/contact/contact.controller.js',

                            '<%= app.source %>/js/i18n.js'
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

    grunt.registerTask('default', ['serve']);
};
