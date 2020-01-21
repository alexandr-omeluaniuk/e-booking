/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import React from 'react';
import PropTypes from 'prop-types';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';
import TableSortLabel from '@material-ui/core/TableSortLabel';
import Checkbox from '@material-ui/core/Checkbox';

function EnhancedTableHead(props) {
    const { classes, onSelectAllClick, order, orderBy, numSelected, rowCount, onRequestSort, headCells } = props;
    const createSortHandler = property => event => {
        onRequestSort(event, property);
    };
    return (
            <TableHead>
                <TableRow>
                    <TableCell padding="checkbox">
                        <Checkbox indeterminate={numSelected > 0 && numSelected < rowCount} checked={numSelected === rowCount}
                            onChange={onSelectAllClick} inputProps={{ 'aria-label': 'select all desserts' }}/>
                    </TableCell>
                    {headCells.map(headCell => (
                        <TableCell key={headCell.id} align={headCell.align}
                            padding={headCell.disablePadding ? 'none' : 'default'} sortDirection={orderBy === headCell.id ? order : false}>
                            <TableSortLabel active={orderBy === headCell.id} direction={orderBy === headCell.id ? order : 'asc'}
                                onClick={createSortHandler(headCell.id)}>
                                {headCell.label}
                                {orderBy === headCell.id ? (
                                    <span className={classes.visuallyHidden}>
                                        {order === 'desc' ? 'sorted descending' : 'sorted ascending'}
                                    </span>
                                ) : null}
                            </TableSortLabel>
                        </TableCell>
                    ))}
                </TableRow>
            </TableHead>
    );
}

EnhancedTableHead.propTypes = {
    classes: PropTypes.object.isRequired,
    numSelected: PropTypes.number.isRequired,
    onRequestSort: PropTypes.func.isRequired,
    onSelectAllClick: PropTypes.func.isRequired,
    order: PropTypes.oneOf(['asc', 'desc']).isRequired,
    orderBy: PropTypes.string.isRequired,
    rowCount: PropTypes.number.isRequired,
    headCells: PropTypes.array.isRequired   // 'right', 'left' allowed
};

export default EnhancedTableHead;
