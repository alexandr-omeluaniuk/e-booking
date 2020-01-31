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
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import MuiDialogTitle from '@material-ui/core/DialogTitle';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import Icon from '@material-ui/core/Icon';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import MomentUtils from '@date-io/moment';
import { MuiPickersUtilsProvider, KeyboardTimePicker, KeyboardDatePicker } from '@material-ui/pickers';
import moment from 'moment';
import "moment/locale/ru";
import i18n from '../../config/i18next-config';
import { green } from '@material-ui/core/colors';

moment.locale(i18n.language);

const useStyles = makeStyles(theme => ({
        fullWidth: {
            width: '100%'
        },
        root: {
            margin: 0,
            padding: theme.spacing(2)
        },
        closeButton: {
            position: 'absolute',
            right: theme.spacing(1),
            top: theme.spacing(1),
            color: theme.palette.grey[500]
        },
        saveButton: {
            color: green[500]
        }
    }));

function StandardForm(props) {
    const classes = useStyles();
    const { t } = useTranslation();
    const dataService = new DataService();
    const { entity, open, handleClose, afterSaveCallback, id } = props;
    // ------------------------------------------ STATE -----------------------------------------------------------------------------------
    const [layout, setLayout] = React.useState(null);
    const [formData, setFormData] = React.useState(new Map());
    const [invalidFields, setInvalidFields] = React.useState(new Map());
    // ------------------------------------------ FUNCTIONS -------------------------------------------------------------------------------
    const sizeOf = (val) => {
        if (val === "false") {
            return false;
        } else if (val === "true") {
            return true;
        } else if (val === "auto") {
            return val;
        } else {
            return parseInt(val);
        }
    };
    const onChangeFieldValue = (name, value) => {
        formData.set(name, value);
        setFormData(new Map(formData));
        isFormValid();
    };
    const renderFormField = (field) => {
        let label = t('models.' + entity + '.' + field.name);
        var isRequired = field.validators.filter(v => {
            return v.type === 'NotNull' || v.type === 'NotEmpty';
        }).length > 0;
        label += isRequired ? ' *' : '';
        let name = field.name;
        if (field.fieldType === 'String') {
            return (
                    <TextField label={label} fullWidth={true} onChange={(e) => onChangeFieldValue(name, e.target.value)}
                            value={formData.has(name) ? formData.get(name) : ''} name={name} error={invalidFields.has(name)}
                            helperText={invalidFields.get(name)}/>
            );
        } else if (field.fieldType === 'DATE') {
            let value = formData.has(name) ? formData.get(name) : null;
            return (
                    <MuiPickersUtilsProvider utils={MomentUtils} libInstance={moment} locale={i18n.language}>
                        <KeyboardDatePicker disableToolbar variant="inline" format={t('constants.momentJsDateFormat')} margin="normal"
                            label={label} onChange={(date) => onChangeFieldValue(name, date)} name={name} value={value} autoOk={true}
                            className={classes.fullWidth} error={invalidFields.has(name)} helperText={invalidFields.get(name)}/>
                    </MuiPickersUtilsProvider>
            );
        }
        return null;
    };
    const isFormValid = () => {
        let newInvalidField = new Map();
        layout.fields.forEach(field => {
            field.validators.forEach(v => {
                let fieldName = field.name;
                let value = formData.has(fieldName) ? formData.get(fieldName) : null;
                if (v.type === 'NotNull' && (value === null || value === undefined)) {
                    newInvalidField.set(fieldName, t('validation.notnull'));
                }
                if (v.type === 'NotEmpty' && (value === null || value === undefined || value.length === 0)) {
                    newInvalidField.set(fieldName, t('validation.notempty'));
                }
                if (v.type === 'Size' && value && (value.length > v.attributes.max)) {
                    newInvalidField.set(fieldName, t('validation.maxLength', {length: v.attributes.max}));
                }
                if (v.type === 'Size' && value && (value.length < v.attributes.min)) {
                    newInvalidField.set(fieldName, t('validation.minLength', {length: v.attributes.min}));
                }
            });
        });
        setInvalidFields(newInvalidField);
        return newInvalidField.size === 0;
    };
    const saveChanges = () => {
        if (isFormValid()) {
            let data = {};
            for (const [key, value] of formData.entries()) {
                let field = layout.fields.filter(f => { return f.name === key; })[0];
                if (field.fieldType === 'DATE') {
                    data[key] = value.format('DD.MM.YYYY');
                } else {
                    data[key] = value;
                }
            }
            handleClose();
            if (formData.has('id')) {
                dataService.requestPut('/entity/' + entity, data).then(resp => {
                    if (afterSaveCallback) {
                        afterSaveCallback();
                    }
                });
            } else {
                dataService.requestPost('/entity/' + entity, data).then(resp => {
                    if (afterSaveCallback) {
                        afterSaveCallback();
                    }
                });
            }
        }
    };
    const load = (layout, data) => {
        if (data) {
            let dataMap = new Map();
            layout.fields.forEach(field => {
                if (data[field.name] !== undefined) {
                    let value = data[field.name];
                    if (field.fieldType === 'DATE') {
                        value = moment(value, t('constants.momentJsDateFormat'));
                    }
                    dataMap.set(field.name, value);
                }
            });
            setFormData(dataMap);
        }
        setLayout(layout);
    };
    // ------------------------------------------ HOOKS -----------------------------------------------------------------------------------
    useEffect(() => {
        if (open) {
            setFormData(new Map());
            dataService.requestGet('/entity/' + entity + '/' + (id ? id : 0)).then(resp => {
                load(resp.layout, resp.data);
            });
        }
    }, [open]);
    // ------------------------------------------ RENDERING -------------------------------------------------------------------------------
    if (layout === null) {
        return null;
    }
    return (
            <Dialog open={open} onClose={handleClose} scroll={'paper'} maxWidth={'md'} fullWidth={true}>
                <MuiDialogTitle disableTypography className={classes.root}>
                    <Typography variant="h6">{t('models.titles.' + entity)}</Typography>
                    <IconButton aria-label="close" className={classes.closeButton} onClick={handleClose}>
                        <Icon>close</Icon>
                    </IconButton>
                </MuiDialogTitle>
                <DialogContent dividers={true}>
                    <Grid container spacing={2}>
                        {layout.fields.map((field, idx) => {
                            if (field.hidden) {
                                return null;
                            }
                            return (
                                    <Grid item xs={sizeOf(field.grid.xs)} lg={sizeOf(field.grid.lg)} md={sizeOf(field.grid.md)}
                                                sm={sizeOf(field.grid.sm)} key={idx}>
                                        {renderFormField(field)}
                                    </Grid>
                            );
                        })}
                    </Grid>
                </DialogContent>
                <DialogActions>
                    <Button className={classes.saveButton} onClick={saveChanges}><Icon>done</Icon> {t('components.window.save')}</Button>
                </DialogActions>
            </Dialog>
    );
}

StandardForm.propTypes = {
    entity: PropTypes.string.isRequired,
    open: PropTypes.bool.isRequired,
    handleClose: PropTypes.func.isRequired,
    afterSaveCallback: PropTypes.func,
    id: PropTypes.number
};

export default StandardForm;