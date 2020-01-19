/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import React from 'react';
import { Switch, Route, Redirect } from "react-router-dom";
import Dashboard from '../view/Dashboard';
import Clients from '../view/Clients';
import AppURLs from '../constants/AppURLs';

export const mainListItems = [{
        icon: 'dashboard',
        label: 'dashboard',
        path: AppURLs.links.view + '/dashboard',
        component: Dashboard
    }, {
        icon: 'contacts',
        label: 'clients',
        path: AppURLs.links.view + '/clients',
        component: Clients
    }];

export function initRouting() {
    let routes = (
            <Switch>
                <Route exact path={AppURLs.context}>
                    <Redirect to={AppURLs.links.view + '/dashboard'}/>
                </Route>
                {mainListItems.map((prop, key) => {
                    return (
                        <Route path={prop.path} component={prop.component} key={key}/>
                    );
                })}
            </Switch>
    );
    return routes;
};
