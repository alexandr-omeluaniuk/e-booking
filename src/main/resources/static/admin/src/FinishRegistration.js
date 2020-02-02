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

import React, { useState } from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Checkbox from '@material-ui/core/Checkbox';
import Box from '@material-ui/core/Box';
import Icon from '@material-ui/core/Icon';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import Copyright from './component/Copyright';
import { useTranslation } from 'react-i18next';
import AppURLs from './constants/AppURLs';
import { history } from './index';

const useStyles = makeStyles(theme => ({
        paper: {
            marginTop: theme.spacing(8),
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center'
        },
        avatar: {
            margin: theme.spacing(1),
            backgroundColor: theme.palette.secondary.main
        },
        form: {
            width: '100%', // Fix IE 11 issue.
            marginTop: theme.spacing(1)
        },
        submit: {
            margin: theme.spacing(3, 0, 2)
        }
    }));

export default function FinishRegistration() {
    const classes = useStyles();
    const { t } = useTranslation();
    const [password, setPassword] = useState('');
    const [passwordRepeat, setPasswordRepeat] = useState('');
    const [passwordValid, setPasswordValid] = useState(true);
    const [passwordRepeatValid, setPasswordRepeatValid] = useState(false);
    const [loginError, setLoginError] = useState('');
    // -------------------------------------------------- METHODS -------------------------------------------------------------------------
    const validation = (p, p2) => {
        let isPasswordValid = p && p.length >= 8;
        let isPasswordRepeatValid = true;
        if (isPasswordValid) {
            isPasswordRepeatValid = p === p2;
        }
        setPasswordValid(isPasswordValid);
        setPasswordRepeatValid(isPasswordRepeatValid);
        return isPasswordValid && isPasswordRepeatValid;
    };
    const finishRegistration = () => {
        
    };
    // -------------------------------------------------- RENDER --------------------------------------------------------------------------
    return (
            <Container component="main" maxWidth="xs">
                <CssBaseline />
                <div className={classes.paper}>
                    <Avatar className={classes.avatar}>
                        <Icon>assignment_ind</Icon>
                    </Avatar>
                    <Typography component="h1" variant="h5">
                        { t('finishRegistrationPage.title') }
                    </Typography>
                    <form className={classes.form} noValidate>
                        <TextField variant="outlined" margin="normal" required fullWidth label={t('finishRegistrationPage.enterPassword')}
                            name="x-password" autoComplete="new-password" autoFocus value={password} type="password" onChange={(e) => {
                                setPassword(e.target.value);
                                validation(e.target.value, passwordRepeat)
                            }} helperText={passwordValid ? null : t('finishRegistrationPage.passwordLength')} error={!passwordValid}/>
                        <TextField variant="outlined" margin="normal" required fullWidth name="x-password-repeat"
                            label={t('finishRegistrationPage.enterPasswordRepeat')}
                            type="password" value={passwordRepeat} autoComplete="new-password" error={!passwordRepeatValid}
                            onChange={(e) => {
                                setPasswordRepeat(e.target.value);
                                validation(password, e.target.value)
                            }} helperText={passwordRepeatValid ? null : t('finishRegistrationPage.passwordNotMatch', {length: 8})} />
                        <Button type="button" fullWidth variant="contained" color="primary" className={classes.submit}
                                onClick={finishRegistration} disabled={!passwordValid || !passwordRepeatValid}>
                            { t('finishRegistrationPage.finishRegistration') }
                        </Button>
                    </form>
                </div>
                <Box mt={8}>
                    <Copyright />
                </Box>
            </Container>
    );
}