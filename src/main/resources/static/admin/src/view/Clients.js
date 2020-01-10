/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import React from 'react';
import EnhancedTable from './../component/datatable/EnhancedTable';

const headCells = [
    {id: 'name', numeric: false, disablePadding: true, label: 'Dessert (100g serving)'},
    {id: 'calories', numeric: true, disablePadding: false, label: 'Calories'},
    {id: 'fat', numeric: true, disablePadding: false, label: 'Fat (g)'},
    {id: 'carbs', numeric: true, disablePadding: false, label: 'Carbs (g)'},
    {id: 'protein', numeric: true, disablePadding: false, label: 'Protein (g)'}
];

const rows = [
    ['Cupcake', 305, 3.7, 67, 4.3],
    ['Donut', 452, 25.0, 51, 4.9],
    ['Eclair', 262, 16.0, 24, 6.0],
    ['Frozen yoghurt', 159, 6.0, 24, 4.0],
    ['Gingerbread', 356, 16.0, 49, 3.9],
    ['Honeycomb', 408, 3.2, 87, 6.5],
    ['Ice cream sandwich', 237, 9.0, 37, 4.3],
    ['Jelly Bean', 375, 0.0, 94, 0.0],
    ['KitKat', 518, 26.0, 65, 7.0],
    ['Lollipop', 392, 0.2, 98, 0.0],
    ['Marshmallow', 318, 0, 81, 2.0],
    ['Nougat', 360, 19.0, 9, 37.0],
    ['Oreo', 437, 18.0, 63, 4.0]
];

function Clients() {
    return (
            <EnhancedTable headCells={headCells} rows={rows}/>
    );
}

export default Clients;
