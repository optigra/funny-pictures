funnyPicturesApp.service('SharedProperties', function () {
    var generatedFunny = { };
    var apiUrl = "http://drawmeme.com/api/";
    return {
        getApiUrl: function () {
            return apiUrl;
        },
        getGeneratedFunny: function () {
            return generatedFunny;
        },
        setGeneratedFunny: function (value) {
            generatedFunny = value;
        }
    };
});