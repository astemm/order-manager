import React from 'react';
import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import OrdersList from "./OrdersList";
import AddOrder from "./AddOrder";
import EditOrder from "./EditOrder";
import ItemsList from './ItemsList';
import AddItem from './AddItem';
import LoginPage from './LoginPage';
import SignUpPage from './SignUpPage';
import { ProtectedRoute } from './ProtectedRoute';

function App() {
  return (
    <div className="App">
      <Router>
              <div className="col-md-6">
                  <h1 className="text-center">React Orders App</h1>
                  <Switch>
                      <ProtectedRoute path="/" exact component={OrdersList} />
                      <ProtectedRoute path="/orders" component={OrdersList} />
                      <ProtectedRoute path="/add-order" component={AddOrder} />
                      <ProtectedRoute path="/edit-order/:orderId" component={EditOrder} />
                      <ProtectedRoute path="/items" component={ItemsList} />
                      <ProtectedRoute path="/add-item" component={AddItem} />
                      <Route path="/login" component={LoginPage} />
                      <Route path="/signup" component={SignUpPage} />
                  </Switch>
              </div>
          </Router>
    </div>
  );
}

export default App;
