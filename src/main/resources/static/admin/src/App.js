import React, {useEffect} from 'react';
import clsx from 'clsx';
import { makeStyles } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import IconButton from '@material-ui/core/IconButton';
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
import Copyright from './component/Copyright';
import SecurityService from './service/SecurityService';
import AppURLs from './constants/AppURLs';
import { Switch, Route, Redirect } from "react-router-dom";
import ListView from './view/ListView';
import { drawerWidth } from './constants/style';
import DesktopToolbar from './component/toolbar/DesktopToolbar';
import DataService from './service/DataService';
import Notification from './component/window/Notification';
import background from './assets/main-background.jpg';

const useStyles = makeStyles(theme => ({
  root: {
    display: 'flex'
  },
  toolbarIcon: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'flex-end',
    padding: '0 8px',
    ...theme.mixins.toolbar
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
        overflow: 'auto',
        "&:after": {
            position: "absolute",
            top: 0,
            bottom: 0,
            left: 0,
            right: 0,
            zIndex: "-1",
            content: '""',
            display: "block",
            opacity: ".3",
            backgroundImage: 'url(' + background + ')',
            backgroundSize: "cover",
            backgroundPosition: "center center"
        }
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
    // ------------------------------------------------ STATE -----------------------------------------------------------------------------
    const [open, setOpen] = React.useState(true);
    const [title, setTitle] = React.useState('');
    const [icon, setIcon] = React.useState(null);
    const [navItems, setNavItems] = React.useState(null);
    const [permissions, setPermissions] = React.useState(null);
    const [openNotification, setOpenNotification] = React.useState(false);
    const [notificationMessage, setNotificationMessage] = React.useState(null);
    const [notificationDetails, setNotificationDetails] = React.useState(null);
    const [notificationType, setNotificationType] = React.useState('info');
    // ------------------------------------------------ METHODS ---------------------------------------------------------------------------
    const showNotification = (msg, details, type) => {
        setNotificationMessage(msg);
        setNotificationDetails(details);
        setNotificationType(type ? type : 'info');
        setOpenNotification(true);
    };
    DataService.setNotification(showNotification);
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
                                setIcon(item.icon);
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
                setIcon(currentRoute.length > 0 ? currentRoute[0].icon : null);
                SecurityService.getPermissions().then(perm => {
                    setPermissions(perm);
                });
            });
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [navItems]);
    // -------------------------------------------------------- RENDER --------------------------------------------------------------------
    return (
            <Router>
                <div className={classes.root}>
                    <CssBaseline />
                    <DesktopToolbar title={title} open={open} setOpen={setOpen} fullname={permissions ? permissions.fullname : ''}
                            icon={icon}/>
                    { createSideBar() }
                    { createMain() }
                </div>
                <Notification open={openNotification} setOpen={setOpenNotification} message={notificationMessage} 
                        details={notificationDetails} severity={notificationType}/>
            </Router>
    );
}

export default App;
