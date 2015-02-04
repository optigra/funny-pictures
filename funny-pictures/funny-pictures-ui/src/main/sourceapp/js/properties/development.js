funnyPicturesApp.service('SharedProperties', function () {
    var generatedFunny = { };
    var apiUrl = "http://localhost:8080/funny-pictures-rest-api/api";
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