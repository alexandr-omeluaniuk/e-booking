/* 
 * Copyright (C) 2018 Wisent Media
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/* global fetch */

import AppURLs from '../constants/AppURLs';

class DataService {
    requestGet = function (url) {
        return this._request('GET', url);
    };
    requestPut = function (url, data) {
        return this._request('PUT', url, data);
    };
    requestPost = function (url, data) {
        return this._request('POST', url, data);
    };
    requestDelete = function (url) {
        return this._request('DELETE', url);
    };
    _request = function (method, url, payload) {
        return fetch(AppURLs.links.rest + url, {
            method: method,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: payload ? JSON.stringify(payload) : null
        }).then(function(response) {
            if (response.ok) {
                return response.json();         
            } else if (response.status === 401) {
                window.location.href = AppURLs.links.welcome;
                return;
            }
//            console.log('Print response: ');
//            console.log(response);
        }).catch(error => {
            console.error('HTTP error occurred: ' + error);
        });
    };
};

export default DataService;
