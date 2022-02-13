import React, { PureComponent } from 'react';
import { userService } from './user.service';

class EditOrder extends React.Component { 
    constructor(props) {
    super(props);

    this.state = {
      id: '',
      price: '',
      quantity: '',
      item: '',
      itemsList: [],
      message: null
    };

    this.saveOrder = this.saveOrder.bind(this);
    this.loadOrder = this.loadOrder.bind(this);
  }

   componentDidMount = () => {
   fetch('http://localhost:8080/items',{headers: userService.authHeader()})  
            .then(response => response.json())  
            .then(data => {
                let i=0;
                data.forEach(it=>it.key1=i++);  
                this.setState({ itemsList: data }); 
            },(error) => {
              if (error) {
                this.setState({ itemsList: [] });
              }});
    console.log('EditOrder mounted');
    this.loadOrder();
  }

  loadOrder() {
    let id = this.props.match.params["orderId"]; 
    fetch('http://localhost:8080/orders/'+id,{headers: userService.authHeader()})  
            .then(response => response.json())  
            .then(data => {
                let order = data; //let order = data.result; 
                this.setState({
                  id: order.id, 
                  price: order.price,
                  quantity: order.quantity,
                  item: order.item
                });  
            }, (error) => {
              if (error) {
                alert('Cannot get order');
              }}); 
  }

  onChange = (e) =>
  this.setState({ [e.target.name]: e.target.value });

  handleChange=(selectedOption) => {
    this.setState({item:this.state.itemsList[selectedOption.target.value]});
                  }

  saveOrder = (e) => {
    e.preventDefault();
    let order = {id:this.state.id, price: this.state.price, quantity: this.state.quantity, item: this.state.item};
    console.log(order);
    fetch('http://localhost:8080/orders/'+this.state.id, {
      method: 'PUT',
      headers: {
          'Authorization':userService.authHeader().Authorization,
          'Content-Type':'application/json'
    },
      body:JSON.stringify(order)
    }).then(res => {
            this.setState({message : 'Order updated successfully.'});
            this.props.history.push('/orders');  
        }, (error) => {
          if (error) {
            alert('Cannot update order. Check data or connection');
          }});
  }

  render () {
    return (
      <div className="EditOrderWrapper">
      <h2  className="text-center">Update Order</h2>
      <form>
      <div className="form-group">
             <label>Order quantity:</label>
             <input type="text" placeholder="quantity" name="quantity" className="form-control" value={this.state.quantity} onChange={this.onChange}/>
      </div>

      <div className="form-group">
             <label>Order price:</label>
             <input type="text" placeholder="price" name="price" className="form-control" value={this.state.price} onChange={this.onChange}/>
      </div>

      <div className="form-group">
             <label>Order item:</label>
             <input type="text" placeholder="item" name="item" className="form-control" value={this.state.item.name+": "+this.state.item.itemPrice} readOnly/>
      </div>

      <div className="form-group">
             <label>New order item:</label>
                    <select className="form-control" data-val="true" name="Item" defaultValue={this.state.item} required  onChange={this.handleChange}>  
                            <option value="">-- Select Item --</option>  
                            {this.state.itemsList.map(item =>
                                <option key={item.key1} value={item.key1}>{item.name+": "+item.itemPrice}</option>
                            )}  
                    </select>
      </div>
      <button className="btn btn-success" onClick={this.saveOrder}>Update</button>
      </form>
      </div>
    );
  }
}

export default EditOrder;
