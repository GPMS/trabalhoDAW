import { Route, BrowserRouter, Redirect } from 'react-router-dom';
import SignIn from 'pages/signin';
import SignUp from 'pages/signup';
import Calendar from 'pages/calendar';

const Routes = () => {
  return (
    <BrowserRouter>
      <Route exact path="/" >
        <Redirect to="/signin" />
      </Route>
      <Route component={SignIn} path="/signin" />
      <Route component={SignUp} path="/signup" />
      <Route component={Calendar} path="/calendar" />
    </BrowserRouter>
  );
}
export default Routes;
