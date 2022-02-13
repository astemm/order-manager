import React, { PureComponent } from 'react';
import AddItem from './AddItem';
import { userService } from './user.service';
import { Link } from 'react-router-dom';

class ItemsList extends React.Component { 
  constructor(props) {
    super(props);

    this.state = {
      items: [],
      isAdded:false,
      message: null,
      loading:false
    };

    this.deleteItem = this.deleteItem.bind(this);
    this.addItem = this.addItem.bind(this);
    this.reloadItemList = this.reloadItemList.bind(this);
    this.handleNested=this.handleNested.bind(this);
    this.handleReload=this.handleReload.bind(this);
  }

  componentDidMount = () => {
    this.reloadItemList();
  }

  handleNested() {
    this.setState({ isAdded:false});
  }

  handleReload() {
    this.reloadItemList();
  }

  reloadItemList() {
    console.log(userService.authHeader());
    fetch('http://localhost:8080/items',{headers: userService.authHeader()})  
            .then(response => response.json())  
            .then(data => {  
                this.setState({ items: data.sort((a,b)=>a.id-b.id), loading: false });  
            }, (error) => {
              if (error) {
                this.setState({ items: [], loading: false });
              }}); 
  }

  deleteItem(id) {
    fetch('http://localhost:8080/items/' + id, {  
      method: 'DELETE',
      headers: userService.authHeader()
      }).then(response => {
         console.log(response.status);
         this.setState({message : 'Item deleted successfully.'});
         this.setState({items: this.state.items.filter(item => item.id !== id)});
     })
  }

  addItem() {
    //this.props.history.push('/add-order');
    this.setState({isAdded : true});
  }

  render () {
    return (
      <div className="ItemsListWrapper">
        <h2 className="text-center">Items Details</h2>
        <div align="left">
        {this.state.isAdded?
        <AddItem action1={this.handleNested} action2={this.handleReload}/>
        :null}
        {this.state.isAdded?null:<button className="btn btn-danger" onClick={() => this.addItem()}> Add Item</button>}
        </div>
        <table className="table table-striped">
          {this.state.items.length ? <thead>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Price</th>
                <th>Delete</th>
            </tr>
          </thead> :null}
          <tbody>
              {this.state.items.map(item =><tr key={item.id}>
                                <td>{item.id}</td>
                                <td>{item.name}</td>
                                <td>{item.itemPrice}</td>
                                <td>
                                    <button className="btn btn-success" onClick={() => this.deleteItem(item.id)}> Delete</button>
                                </td>
                                </tr>
                            )
                        }
            </tbody>
        </table>
        <br></br>
        <div align="left">
        <Link to="/orders">Return to Orders</Link>
        <br></br>
        <Link to="/login">Log out</Link>
        </div>
      </div>
    );
  }
}

export default ItemsList;
