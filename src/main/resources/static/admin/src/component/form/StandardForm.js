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
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import MomentUtils from '@date-io/moment';
import { MuiPickersUtilsProvider, KeyboardTimePicker, KeyboardDatePicker } from '@material-ui/pickers';
import moment from 'moment';
import "moment/locale/ru";
import i18n from '../../config/i18next-config';

console.log(i18n.language);
moment.locale(i18n.language);

const useStyles = makeStyles(theme => ({
        fullWidth: {
            width: '100%'
        }
    }));

function StandardForm(props) {
    const classes = useStyles();
    const { t } = useTranslation();
    const dataService = new DataService();
    const { entity } = props;
    // ------------------------------------------ STATE -----------------------------------------------------------------------------------
    const [load, setLoad] = React.useState(true);
    const [layout, setLayout] = React.useState(null);
    const [formData, setFormData] = React.useState({});
    const [formChanged, setFormChanged] = React.useState(false);
    // ------------------------------------------ FUNCTIONS -------------------------------------------------------------------------------
    const onChangeFieldValue = (name, value) => {
        formData[name] = value;
        setFormData(formData);
        setFormChanged(!formChanged);
    };
    const createFormField = (field) => {
        let label = t('models.' + entity + '.' + field.name);
        let name = field.name;
        if (field.fieldType === 'String') {
            return (
                    <TextField label={label} fullWidth={true} onChange={(e) => onChangeFieldValue(name, e.target.value)}
                            value={formData[name]} name={name}/>
            );
        } else if (field.fieldType === 'Date') {
            let value = formData[name] === undefined ? null : formData[name];
            return (
                    <MuiPickersUtilsProvider utils={MomentUtils} libInstance={moment} locale={i18n.language}>
                        <KeyboardDatePicker disableToolbar variant="inline" format={t('constants.momentJsDateFormat')} margin="normal"
                            label={label} onChange={(date) => onChangeFieldValue(name, date)} name={name} value={value} autoOk={true}
                            className={classes.fullWidth}/>
                    </MuiPickersUtilsProvider>
            );
        }
        return null;
    };
    // ------------------------------------------ HOOKS -----------------------------------------------------------------------------------
    useEffect(() => {
        if (load) {
            dataService.requestGet('/entity/layout/' + entity).then(resp => {
                if (resp) {
                    setLoad(false);
                    setLayout(resp);
                }
            });
        }
    }, [load, entity, dataService]);
    // ------------------------------------------ RENDERING -------------------------------------------------------------------------------
    if (layout === null) {
        return null;
    }
    return (
            <Grid container>
                {layout.fields.map((field, idx) => {
                    return (
                            <Grid item xs={12} key={idx}>
                                {createFormField(field)}
                            </Grid>
                    );
                })}
            </Grid>
    );
}

StandardForm.propTypes = {
    entity: PropTypes.string.isRequired
};

export default StandardForm;