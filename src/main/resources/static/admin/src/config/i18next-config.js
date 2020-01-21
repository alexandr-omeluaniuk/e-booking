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
                menu: {
                    dashboard: "Обзор",
                    clients: 'Клиенты',
                    subscriptions: 'Подписки'
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
                    minLength: 'Не короче {{length}} символов'
                },
                subscriptions: {
                    title: 'Обзор подписок'
                },
                models: {
                    'subscription': {
                        organizationName: 'Название организации',
                        started: 'Начало подписки',
                        expirationDate: 'Окончание подписки'
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