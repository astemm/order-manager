import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { userService } from './user.service';

class OrdersList extends React.Component { 
  constructor(props) {
    super(props);
    this.state = {
      orders: [],
      message: null,
      loading:false
    };
        this.deleteOrder = this.deleteOrder.bind(this);
        this.editOrder = this.editOrder.bind(this);
        this.addOrder = this.addOrder.bind(this);
        this.reloadOrderList = this.reloadOrderList.bind(this);
  }

  componentWillMount = () => {
    console.log('OrdersList will mount');
  }

  componentDidMount = () => {
    this.reloadOrderList();
  }

  reloadOrderList() {
    fetch('http://localhost:8080/orders',{headers: userService.authHeader()})  
            .then(response => response.json())  
            .then(data => {
                console.log(data);  
                this.setState({ orders: data, loading: false });  
            }, (error) => {
              if (error) {
                this.setState({ orders: [], loading: false });
              }}); 
  }

  deleteOrder(id) {
    fetch('http://localhost:8080/orders/' + id, {
      headers: userService.authHeader(),
      method: 'DELETE'  
  }).then(response => {
         this.setState({message : 'Order deleted successfully.'});
         this.setState({orders: this.state.orders.filter(order => order.id !== id)});
     })
}

  editOrder(id) {
    this.props.history.push('/edit-order/' + id);
  }


  addOrder() {
    this.props.history.push('/add-order');
  }

  manageItems() {
    this.props.history.push('/items');
  }

  render () {
    if (this.state.hasError) {
      return <h1>There is error</h1>;
    }
    return (
      <div className="OrdersListWrapper">
        <h2 className="text-center">Orders Details</h2>
        <div>
        <button className="btn btn-danger" onClick={() => this.manageItems()}> Manage Items</button>
        </div>
        <button className="btn btn-danger" onClick={() => this.addOrder()}> Add Order</button>
        <table className="table table-striped">
          {this.state.orders.length ? <thead>
            <tr>
                <th>Id</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Item-Id</th>
                <th>Item-Name</th>
                <th>Edit/Delete</th>
            </tr>
          </thead> :null}
          <tbody>
              {this.state.orders.length?this.state.orders.map(order =><tr key={order.id}>
                                <td>{order.id}</td>
                                <td>{order.price}</td>
                                <td>{order.quantity}</td>
                                <td>{order.item.id}</td>
                                <td>{order.item.name}</td>
                                <td>
                                    <button className="btn btn-success" onClick={() => this.deleteOrder(order.id)}> Delete</button>
                                    <button className="btn btn-success" onClick={() => this.editOrder(order.id)}> Edit</button>
                                </td>
                                </tr>
                            )
                        :null}
            </tbody>
        </table>
        <p>
          {localStorage.getItem('user')?<Link to="/login">Log out</Link>:null}
        </p>
        </div>
    );
  }
}

export default OrdersList;
