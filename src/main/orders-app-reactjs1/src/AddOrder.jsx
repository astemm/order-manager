import React, { PureComponent } from 'react';
import { userService } from './user.service'; 

class AddOrder extends React.Component { 
  constructor(props) {
    super(props);

    this.state ={
      price: '',
      quantity: '',
      item: '',
      itemsList: [],
      message: null
  }
  
  this.saveOrder = this.saveOrder.bind(this);
  }

  saveOrder = (e) => {
    e.preventDefault();
    let order = {price: this.state.price, quantity: this.state.quantity, item: this.state.item};
    console.log(order);
    fetch('http://localhost:8080/orders', {  
      method: 'POST',
      headers: {
        'Authorization':userService.authHeader().Authorization,
        'Content-Type':'application/json'},
      body:JSON.stringify(order)
    }).then(res => {
            this.setState({message : 'Order added successfully.'});
            this.props.history.push('/orders');
        }, (error) => {
          if (error) { console.log(error);
            alert('Cannot add order. Check data or connection');
          }});
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
  }

  onChange = (e) => 
        this.setState({ [e.target.name]: e.target.value });
  
  handleChange=(selectedOption) => {
    this.setState({item:this.state.itemsList[selectedOption.target.value]});
    console.log(selectedOption);
                  }
  

  render () {
    return (
      <div className="AddOrderWrapper">
      <h2  className="text-center">Add Order</h2>
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
                    <select className="form-control" data-val="true" name="Item" defaultValue={this.state.item} required  onChange={this.handleChange}>  
                            <option value="">-- Select Item --</option>  
                            {this.state.itemsList.map(item =>  
                                <option key={item.key1} value={item.key1}>{item.name+": "+item.itemPrice}</option>  
                            )}  
                    </select>
      </div>

      <button className="btn btn-success" onClick={this.saveOrder}>Save</button>
      </form>
      </div>
    );
  }
}

export default AddOrder;
