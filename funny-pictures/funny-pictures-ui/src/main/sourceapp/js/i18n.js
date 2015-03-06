(function() {
    'use strict';

    angular
        .module('app')
        .config(translation);

    translation
        .$inject = [
            '$translateProvider'
        ];

    function translation($translateProvider) {
        $translateProvider.translations('en_US', {
            CONTACT_HEADER: "Feel free to contact us",
            NAME_LABEL: "Name",
            EMAIL_LABEL: "Email",
            SUBJECT_LABEL: "Subject",
            MESSAGE_LABEL: "Message",
            SEND_MESSAGE_BUTTON: "Send Message",
            OUR_CONTACTS_LABEL: "Our contacts",
            ADDRESS: "12 Storozhenka St., Lviv",
            CITY: "[Galicia] Ukraine",
            UPLOAD_PICTURE_HEADER: "Upload your template",
            TEMPLATE_TITLE_LABEL: "Template title",
            IMAGE_URL_LABEL: "Input image url",
            OR_LABEL: "or",
            OPEN_IMAGE_BUTTON: "Open image",
            FILE_NAME_LABEL: "File name",
            UPLOAD_IMAGE_BUTTON: "Upload file",
            DOWNLOAD_LABEL: "Download",
            SHARE_LABEL: "Share",
            HEADER_TEXT_LABEL: "Header text",
            FOOTER_TEXT_LABEL: "Footer text",
            CREATE_FUNNY_BUTTON: "Create picture",
            CANCEL_BUTTON: "Cancel",
            CREATE_NEW_BUTTON: "Create new",
            HOME_HEADER: "Home",
            TEMPLATES_HEADER: "Templates",
            CREATE_TEMPLATE_HEADER: "Create template",
            CONTACT_US_HEADER: "Contact us",
            COMMENTS_FALLBACK_1: "Please enable JavaScript to view the",
            COMMENTS_FALLBACK_2: "comments powered by Disqus.",
            PREVIOUS_LABEL: "Previous",
            NEXT_LABEL: "Next",
            LINK_TO_IMAGE: "Direct link to image",
            LINK_TO_PREVIEW: "Link to preview",
            COPY_LABEL: "Copy"
        });

        $translateProvider.translations('uk', {
            CONTACT_HEADER: "Зв\'яжіться з нами",
            NAME_LABEL: "Ім\'я",
            EMAIL_LABEL: "Електронна пошта",
            SUBJECT_LABEL: "Тема",
            MESSAGE_LABEL: "Повідомлення",
            SEND_MESSAGE_BUTTON: "Відслати",
            OUR_CONTACTS_LABEL: "Наші контакти",
            ADDRESS: "вул.Стороженка 12, Львів",
            CITY: "[Галичина] Україна",
            UPLOAD_PICTURE_HEADER: "Завантажте ваше зображення",
            TEMPLATE_TITLE_LABEL: "Назва картинки",
            IMAGE_URL_LABEL: "Введіть URL-адресу зображення",
            OR_LABEL: "або",
            OPEN_IMAGE_BUTTON: "Відкрити зображення",
            FILE_NAME_LABEL: "Назва файлу",
            UPLOAD_IMAGE_BUTTON: "Завантажити зображення",
            DOWNLOAD_LABEL: "Завантажити",
            SHARE_LABEL: "Поділитися",
            HEADER_TEXT_LABEL: "Верхній текст",
            FOOTER_TEXT_LABEL: "Нижній текст",
            CREATE_FUNNY_BUTTON: "Створити мем",
            CANCEL_BUTTON: "Відмінити",
            CREATE_NEW_BUTTON: "Створити новий мем",
            HOME_HEADER: "Головна",
            TEMPLATES_HEADER: "Шаблони",
            CREATE_TEMPLATE_HEADER: "Створити шаблон",
            CONTACT_US_HEADER: "Контакти",
            COMMENTS_FALLBACK_1: "Будь ласка, включіть JavaScript для перегляду",
            COMMENTS_FALLBACK_2: "Disqus коментарів.",
            PREVIOUS_LABEL: "Попередня",
            NEXT_LABEL: "Наступна",
            LINK_TO_IMAGE: "Пряме посилання на картинку",
            LINK_TO_PREVIEW: "Посилання на попередній перегляд",
            COPY_LABEL: "Копіювати"
        });

        $translateProvider.determinePreferredLanguage();
        $translateProvider.fallbackLanguage('en_US');
    }

})();
