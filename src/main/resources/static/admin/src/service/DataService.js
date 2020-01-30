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
import { history } from '../index';

class DataService {
    constructor() {
        this.abortController = new AbortController();
    }
    requestGet = (url) => {
        return this._request('GET', url);
    };
    requestPut = (url, data) => {
        return this._request('PUT', url, data);
    };
    requestPost = (url, data) => {
        return this._request('POST', url, data);
    };
    requestDelete = (url) => {
        return this._request('DELETE', url);
    };
    abort = () => {
        this.abortController.abort();
    }
    _request = (method, url, payload) => {
        let signal = this.abortController.signal;
        return fetch(AppURLs.links.rest + url, {
            method: method,
            signal: signal,
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: payload ? JSON.stringify(payload) : null
        }).then(function(response) {
            if (response.ok) {
                return response.json();
            } else if (response.status === 401) {
                history.push(AppURLs.links.welcome);
                throw new Error('Unauthorized request!');
            }
        }).catch(error => {
            console.error('HTTP error occurred: ' + error);
        });
    };
};

export default DataService;
