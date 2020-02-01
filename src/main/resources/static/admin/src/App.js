import React, {useEffect} from 'react';
import clsx from 'clsx';
import { makeStyles } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import Badge from '@material-ui/core/Badge';
import Icon from '@material-ui/core/Icon';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import List from '@material-ui/core/List';
import Drawer from '@material-ui/core/Drawer';
import Divider from '@material-ui/core/Divider';
import ListItemText from '@material-ui/core/ListItemText';
import Container from '@material-ui/core/Container';
import Box from '@material-ui/core/Box';
import { NavLink, BrowserRouter as Router } from "react-router-dom";
import { useTranslation } from 'react-i18next';
import Copyright from './component/Copyright';
import SecurityService from './service/SecurityService';
import AppURLs from './constants/AppURLs';
import { Switch, Route, Redirect } from "react-router-dom";
import ListView from './view/ListView';

const drawerWidth = 240;

const useStyles = makeStyles(theme => ({
  root: {
    display: 'flex'
  },
  toolbar: {
    paddingRight: 24 // keep right padding when drawer closed
  },
  toolbarIcon: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
    padding: '0 8px',
    ...theme.mixins.toolbar
  },
  appBar: {
    zIndex: theme.zIndex.drawer + 1,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen
    })
  },
  appBarShift: {
    marginLeft: drawerWidth,
    width: `calc(100% - ${drawerWidth}px)`,
    transition: theme.transitions.create(['width', 'margin'], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen
    })
  },
  menuButton: {
    marginRight: 36
  },
  menuButtonHidden: {
    display: 'none'
  },
  title: {
    flexGrow: 1
  },
  drawerPaper: {
    position: 'relative',
    whiteSpace: 'nowrap',
    width: drawerWidth,
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen
    })
  },
  drawerPaperClose: {
    overflowX: 'hidden',
    transition: theme.transitions.create('width', {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen
    }),
    width: theme.spacing(7),
    [theme.breakpoints.up('sm')]: {
      width: theme.spacing(9)
    }
  },
  appBarSpacer: theme.mixins.toolbar,
  content: {
    flexGrow: 1,
    height: '100vh',
    overflow: 'auto'
  },
  container: {
    paddingTop: theme.spacing(4),
    paddingBottom: theme.spacing(4),
    maxWidth: 'none'
  },
  paper: {
    padding: theme.spacing(2),
    display: 'flex',
    overflow: 'auto',
    flexDirection: 'column'
  },
  fixedHeight: {
    height: 240
  },
  navLink: {
      textDecoration: 'none',
      color: 'inherit'
  }
}));


function App() {
    const classes = useStyles();
    const { t } = useTranslation();
    // ------------------------------------------------ STATE -----------------------------------------------------------------------------
    const [open, setOpen] = React.useState(true);
    const [title, setTitle] = React.useState('');
    const [navItems, setNavItems] = React.useState(null);
    // ------------------------------------------------ METHODS ---------------------------------------------------------------------------
    const createToolbar = () => {
        return (
            <AppBar position="absolute" className={clsx(classes.appBar, open && classes.appBarShift)}>
                <Toolbar className={classes.toolbar}>
                    <IconButton edge="start" color="inherit" aria-label="open drawer" onClick={() => {setOpen(true);}}
                        className={clsx(classes.menuButton, open && classes.menuButtonHidden)}>
                        <Icon>menu</Icon>
                    </IconButton>
                    <Typography component="h1" variant="h6" color="inherit" noWrap className={classes.title}>
                        { title }
                    </Typography>
                    <IconButton color="inherit">
                        <Badge badgeContent={4} color="secondary">
                            <Icon>notifications</Icon>
                        </Badge>
                    </IconButton>
                </Toolbar>
            </AppBar>
        );
    };
    const createSideBarNavigation = () => {
        if (!navItems) {
            return null;
        }
        return (
                <div>
                    {navItems.map((item, i) => {
                        return (
                            <NavLink to={item.path} key={i} className={classes.navLink} onClick={() => {
                                setTitle(item.label);
                            }}>
                                <ListItem button selected={window.location.pathname === item.path}>
                                    <ListItemIcon>
                                        <Icon>{item.icon}</Icon>
                                    </ListItemIcon>
                                    <ListItemText primary={item.label} />
                                </ListItem>
                            </NavLink>
                        );
                    })}
                </div>
        );
    };
    const createSideBar = () => {
        return (
            <Drawer variant="permanent"
                classes={{
                    paper: clsx(classes.drawerPaper, !open && classes.drawerPaperClose)
                }} open={open}>
                <div className={classes.toolbarIcon}>
                    <IconButton onClick={() => {setOpen(false);}}>
                        <Icon>chevron_left</Icon>
                    </IconButton>
                </div>
                <Divider />
                <List>{createSideBarNavigation()}</List>
            </Drawer>
        );
    };
    const createMain = () => {
        if (!navItems) {
            return null;
        }
        let routes = (
            <Switch>
                <Route exact path={AppURLs.context}>
                    <Redirect to={AppURLs.links.view + '/dashboard'}/>
                </Route>
                {navItems.map((prop, key) => {
                    if (prop.metadata) {
                        return (
                            <Route path={prop.path} render={() => <ListView metadata={prop.metadata}/>} key={key}/>
                        );
                    } else {
                        return (
                            <Route path={prop.path} component={prop.component} key={key}/>
                        );
                    }
                })}
            </Switch>
        );
        return (
                <main className={classes.content}>
                    <div className={classes.appBarSpacer} />
                        <Container maxWidth="lg" className={classes.container}>
                            { routes }
                            <Box pt={4}>
                                <Copyright />
                            </Box>
                        </Container>
                </main>
        );
    };
    // -------------------------------------------------------- HOOKS ---------------------------------------------------------------------
    useEffect(() => {
        if (!navItems) {
            SecurityService.getNavigation().then(p => {
                setNavItems(p);
                let currentRoute = p.filter(item => { return window.location.pathname === item.path; });
                setTitle(currentRoute.length > 0 ? currentRoute[0].label : '');
            });
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [navItems]);
    // -------------------------------------------------------- RENDER --------------------------------------------------------------------
    return (
            <Router>
                <div className={classes.root}>
                    <CssBaseline />
                    { createToolbar() }
                    { createSideBar() }
                    { createMain() }
                </div>
            </Router>
    );
}

export default App;
