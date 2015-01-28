funnyPicturesApp.service('SharedProperties', function () {
    var generatedFunny = { };
    var apiUrl = "http://drawmeme.com/api/";
    var templateId = 0;
    return {
        getApiUrl: function () {
            return apiUrl;
        },
        getGeneratedFunny: function () {
            return generatedFunny;
        },
        setGeneratedFunny: function (value) {
            generatedFunny = value;
        },
        setTemplateId: function (id) {
            templateId = id;
        },
        getTemplateId: function () {
            return templateId;
        }

    };
});