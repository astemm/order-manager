import React, { PureComponent } from 'react';
import { userService } from './user.service';

class AddItem extends React.Component { 
  constructor(props) {
    super(props);

    this.state = {
      name: '',
      itemPrice: '',
      namesList: [],
      message: null
    };
    this.saveItem = this.saveItem.bind(this);
  }

  saveItem = (e) => {
    e.preventDefault();
    let item = {name: this.state.name, itemPrice: this.state.itemPrice};
    console.log(item);
    fetch('http://localhost:8080/items', {  
      method: 'POST',
      headers: {
        'Authorization':userService.authHeader().Authorization,
        'Content-Type':'application/json'},
      body:JSON.stringify(item)
    }).then(res => { //console.log(res);
            this.setState({message : 'Item added successfully.'});
            this.props.action2();
           // this.props.history.push('/orders');
        }, (error) => {
          if (error) { console.log(error);
            alert('Cannot add item. Check data or connection');
          }});
  }

  componentDidMount = () => {
    fetch('http://localhost:8080/items/names',
    {headers: userService.authHeader()})  
            .then(response => response.json())  
            .then(data => {  
                this.setState({ namesList: data });  
            },(error) => {
              if (error) {
                this.setState({ namesList: [] });
              }});
  }

  onChange = (e) => 
        this.setState({ [e.target.name]: e.target.value });
  
  handleChange=(selectedOption) => {
    this.setState({name:selectedOption.target.value});
    console.log(selectedOption.target.value); //name:this.state.namesList[selectedOption.target.value-1]
                  }


  render () {
    return (
      <div className="AddItemWrapper">
      <h2  className="text-center">Add Item</h2>
      <form>
      <div className="form-group">
             <label>Item price:</label>
             <input type="text" placeholder="price" name="itemPrice" className="form-control" value={this.state.itemPrice} onChange={this.onChange}/>
      </div>

      <div className="form-group">
             <label>Item name:</label>
                    <select className="form-control" data-val="true" name="Item" defaultValue={this.state.name} required  onChange={this.handleChange}>  
                            <option value="">-- Select Name --</option>  
                            {this.state.namesList.map(name =>  
                                <option key={name} value={name}>{name}</option>  
                            )}  
                    </select>
      </div>

      <button className="btn btn-success" onClick={this.saveItem}>Save</button>
      <button className="btn btn-success" onClick={this.props.action1}>Cancel</button>
      </form>
      {this.state.message?this.state.message:null}
      </div>
    );
  }
}

export default AddItem;
