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
                        expirationDate: 'Окончание подписки'
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