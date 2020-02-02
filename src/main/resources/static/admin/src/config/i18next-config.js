/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import i18n from "i18next";
import { initReactI18next } from "react-i18next";

i18n.use(initReactI18next).init({
    resources: {
        ru: {
            translation: {
                toolbar: {
                    accountmenu: {
                        logout: 'Выход'
                    }
                },
                constants: {
                    momentJsDateFormat: 'DD.MM.YYYY'
                },
                menu: {
                    dashboard: "Обзор"
                },
                loginPage: {
                    signIn: 'Войти в систему',
                    forgotPassword: 'Забыли пароль?',
                    emailAddress: 'Адрес электронной почты',
                    password: 'Пароль',
                    rememberMe: 'Запомнить меня',
                    err: {
                        userNotFound: 'Данный адрес электронной почты не зарегистрирован',
                        badPassword: 'Неверный пароль',
                        userDeactivated: 'Учетная запись деактивирована'
                    }
                },
                finishRegistrationPage: {
                    title: 'Завершение регистрации',
                    finishRegistration: 'Завершить регистрацию',
                    enterPassword: 'Введите пароль',
                    enterPasswordRepeat: 'Повторите пароль',
                    passwordLength: 'Не короче 8 символов',
                    passwordNotMatch: 'Пароли не совпадают',
                    invalidLink: 'Ссылка больше недействительна'
                },
                validation: {
                    email: 'Неверный формат адреса электронной почты',
                    minLength: 'Не короче {{length}} символов',
                    maxLength: 'Не более {{length}} символов',
                    notnull: 'Обязательное поле',
                    notempty: 'Обязательное поле'
                },
                models: {
                    titles: {
                        Subscription: 'Подписка',
                        SystemUser: 'Пользователь',
                        many: {
                            Subscription: 'Подписки',
                            SystemUser: 'Пользователи'
                        }
                    },
                    Subscription: {
                        organizationName: 'Название организации',
                        started: 'Начало подписки',
                        expirationDate: 'Окончание подписки',
                        subscriptionAdminEmail: 'Адрес электронной почты администратора'
                    },
                    SystemUser: {
                        firstname: 'Имя',
                        lastname: 'Фамилия',
                        status: 'Статус',
                        standardRole: 'Стандартная роль',
                        email: 'Email'
                    }
                },
                components: {
                    datatable: {
                        densePadding: 'Компактная таблица',
                        selectedRecords: 'Выбрано записей',
                        clearSelection: 'Очистить выделение',
                        toolbar: {
                            filter: 'Фильтр',
                            add: 'Новая запись'
                        }
                    },
                    window: {
                        save: 'Сохранить'
                    }
                },
                common: {
                    delete: 'Удалить',
                    edit: 'Редактировать'
                },
                enum: {
                    SystemUserStatus: {
                        ACTIVE: 'Активный',
                        INACTIVE: 'Деактивирован',
                        REGISTRATION: 'В процессе регистрации'
                    },
                    StandardRole: {
                        ROLE_SUPER_ADMIN: 'Супер админстратор',
                        ROLE_SUBSCRIPTION_ADMINISTRATOR: 'Администратор',
                        ROLE_SUBSCRIPTION_USER: 'Пользователь'
                    }
                }
            }
        }
    },
    lng: "ru",
    fallbackLng: "ru",
    debug: false,
    interpolation: {
        escapeValue: false
    }
});

export default i18n;