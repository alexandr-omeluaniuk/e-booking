/* 
 * The MIT License
 *
 * Copyright 2020 ss.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import { useTranslation } from 'react-i18next';
import DataService from '../../service/DataService';

const useStyles = makeStyles(theme => ({
        
    }));

function StandardForm(props) {
    const classes = useStyles();
    const { t } = useTranslation();
    const dataService = new DataService();
    const { entity } = props;
    // ------------------------------------------ STATE -----------------------------------------------------------------------------------
    const [load, setLoad] = React.useState(true);
    const [layout, setLayout] = React.useState(null);
    // ------------------------------------------ HOOKS -----------------------------------------------------------------------------------
    useEffect(() => {
        if (load) {
            dataService.requestGet('/entity/layout/' + 'Subscription').then(resp => {
                setLoad(false);
                setLayout(resp);
            });
        }
    }, [load, entity, dataService]);
    // ------------------------------------------ RENDERING -------------------------------------------------------------------------------
    if (layout === null) {
        return null;
    }
    return (
            <div>Standard Form</div>
    );
}

StandardForm.propTypes = {
    entity: PropTypes.string.isRequired
};

export default StandardForm;