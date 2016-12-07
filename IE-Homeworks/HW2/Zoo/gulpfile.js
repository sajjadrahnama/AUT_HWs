/**
 * Created by sajjad on 4/22/16.
 */
var gulp = require('gulp');
var sass = require('gulp-sass');


gulp.task('watch',function(){
    gulp.watch('resources/**/*.scss',function(){
        gulp.src(['resources/zoomg.scss'])
            .pipe(sass())
            .pipe(gulp.dest('stylesheets/'));
        gulp.src(['resources/zoomit.scss'])
            .pipe(sass())
            .pipe(gulp.dest('stylesheets/'));
    });
});


gulp.task('default',['watch']);