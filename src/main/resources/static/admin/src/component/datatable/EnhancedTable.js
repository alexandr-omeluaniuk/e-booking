/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import Paper from '@material-ui/core/Paper';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableRow from '@material-ui/core/TableRow';
import Checkbox from '@material-ui/core/Checkbox';
import TablePagination from '@material-ui/core/TablePagination';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Switch from '@material-ui/core/Switch';
import { makeStyles } from '@material-ui/core/styles';
import EnhancedTableToolbar from './EnhancedTableToolbar';
import EnhancedTableHead from './EnhancedTableHead';
import DataService from '../../service/DataService';
import { useTranslation } from 'react-i18next';

const useStyles = makeStyles(theme => ({
        root: {
            width: '100%'
        },
        paper: {
            width: '100%',
            marginBottom: theme.spacing(2)
        },
        table: {
            minWidth: 750
        },
        visuallyHidden: {
            border: 0,
            clip: 'rect(0 0 0 0)',
            height: 1,
            margin: -1,
            overflow: 'hidden',
            padding: 0,
            position: 'absolute',
            top: 20,
            width: 1
        }
    }));

function EnhancedTable(props) {
    const classes = useStyles();
    const { t } = useTranslation();
    const dataService = new DataService();
    const {headCells, title, entity } = props;
    // ----------------------------------------------- STATE ------------------------------------------------------------------------------
    const [rows, setRows] = React.useState([]);
    const [load, setLoad] = React.useState(true);
    const [order, setOrder] = React.useState('asc');
    const [orderBy, setOrderBy] = React.useState(headCells[0].id);
    const [selected, setSelected] = React.useState([]);
    const [page, setPage] = React.useState(0);
    const [dense, setDense] = React.useState(false);
    const [rowsPerPage, setRowsPerPage] = React.useState(5);
    // ----------------------------------------------- FUNCTIONS --------------------------------------------------------------------------
    const reloadTable = () => {
        setLoad(true);
    };
    const handleRequestSort = (event, property) => {
        const isAsc = orderBy === property && order === 'asc';
        setOrder(isAsc ? 'desc' : 'asc');
        setOrderBy(property);
    };
    const handleSelectAllClick = event => {
        if (event.target.checked) {
            const newSelecteds = rows.map(n => n.name);
            setSelected(newSelecteds);
            return;
        }
        setSelected([]);
    };
    const handleClick = (event, name) => {
        const selectedIndex = selected.indexOf(name);
        let newSelected = [];
        if (selectedIndex === -1) {
            newSelected = newSelected.concat(selected, name);
        } else if (selectedIndex === 0) {
            newSelected = newSelected.concat(selected.slice(1));
        } else if (selectedIndex === selected.length - 1) {
            newSelected = newSelected.concat(selected.slice(0, -1));
        } else if (selectedIndex > 0) {
            newSelected = newSelected.concat(
                    selected.slice(0, selectedIndex),
                    selected.slice(selectedIndex + 1),
                    );
        }
        setSelected(newSelected);
    };
    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };
    const handleChangeRowsPerPage = event => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };
    const handleChangeDense = event => {
        setDense(event.target.checked);
    };
    const getSorting = (order, orderBy) => {
        return order === 'desc' ? (a, b) => desc(a, b, orderBy) : (a, b) => -desc(a, b, orderBy);
    };
    const desc = (a, b, orderBy) => {
        if (b[orderBy] < a[orderBy]) {
            return -1;
        }
        if (b[orderBy] > a[orderBy]) {
            return 1;
        }
        return 0;
    };
    const stableSort = (array, cmp) => {
        const stabilizedThis = array.map((el, index) => [el, index]);
        stabilizedThis.sort((a, b) => {
            const order = cmp(a[0], b[0]);
            if (order !== 0)
                return order;
            return a[1] - b[1];
        });
        return stabilizedThis.map(el => el[0]);
    };
    const isSelected = name => selected.indexOf(name) !== -1;
    const emptyRows = rowsPerPage - Math.min(rowsPerPage, rows.length - page * rowsPerPage);
    // --------------------------------------------------- HOOKS --------------------------------------------------------------------------
    useEffect(() => {
        if (load) {
            dataService.requestPost('/entity/search/' + entity, {
                page: page + 1,
                pageSize: rowsPerPage
            }).then(resp => {
                if (resp) {
                    setLoad(false);
                    setRows(resp.data);
                }
            });
        }
    }, [load, entity, page, rowsPerPage, dataService]);
    useEffect(() => {
        return () => {
            dataService.abort();
        };
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);
    // --------------------------------------------------- RENDER -------------------------------------------------------------------------
    return (
            <div className={classes.root}>
                <Paper className={classes.paper}>
                    <EnhancedTableToolbar numSelected={selected.length} title={title} entity={entity} reloadTable={reloadTable}/>
                    <TableContainer>
                        <Table className={classes.table} aria-labelledby="tableTitle" size={dense ? 'small' : 'medium'}
                            aria-label="enhanced table">
                            <EnhancedTableHead classes={classes} numSelected={selected.length} order={order} orderBy={orderBy} headCells={headCells}
                                onSelectAllClick={handleSelectAllClick} onRequestSort={handleRequestSort} rowCount={rows.length} entity={entity}/>
                            <TableBody>
                            {stableSort(rows, getSorting(order, orderBy)).slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((row, index) => {
                                const isItemSelected = isSelected(row[0]);
                                const labelId = `enhanced-table-checkbox-${index}`;
                                return (
                                    <TableRow hover onClick={event => handleClick(event, row[0])} role="checkbox" key={index}
                                        aria-checked={isItemSelected} tabIndex={-1} selected={isItemSelected}>
                                        <TableCell padding="checkbox">
                                            <Checkbox checked={isItemSelected} inputProps={{ 'aria-labelledby': labelId }}/>
                                        </TableCell>
                                        {headCells.map((column, i) => {
                                            if (i === 0) {
                                                return (
                                                    <TableCell component="th" scope="row" padding="none" key={i} id={labelId}>
                                                        {row[column.id]}
                                                    </TableCell> 
                                                );
                                            } else {
                                                return (
                                                    <TableCell align="right" key={i}>{row[column.id]}</TableCell>
                                                );
                                            }
                                        })}
                                    </TableRow>
                                );
                            })}
                            {emptyRows > 0 && (
                                <TableRow style={{ height: (dense ? 33 : 53) * emptyRows }}>
                                    <TableCell colSpan={6} />
                                </TableRow>
                            )}
                            </TableBody>
                        </Table>
                    </TableContainer>
                    <TablePagination rowsPerPageOptions={[5, 10, 25]} component="div" count={rows.length} rowsPerPage={rowsPerPage}
                        page={page} onChangePage={handleChangePage} onChangeRowsPerPage={handleChangeRowsPerPage}/> 
                </Paper>
                <FormControlLabel control={<Switch checked={dense} onChange={handleChangeDense} />} label={t('components.datatable.densePadding')} />
            </div>
    );
}

EnhancedTable.propTypes = {
    entity: PropTypes.string.isRequired,
    headCells: PropTypes.array.isRequired,
    title: PropTypes.string
};

export default EnhancedTable;
