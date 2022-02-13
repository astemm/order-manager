import React, { PureComponent } from 'react';
import { userService } from './user.service';
import { Link } from 'react-router-dom';

class SignUpPage extends React.Component { 
  constructor(props) {
    super(props);

    this.state = {
      username: '',
      password: '',
      name: '',
      email: '',
      roles: [{id: 1, value: "ROLE_ADMIN", isChecked: false},
      {id: 2, value: "ROLE_GUEST", isChecked: false}],
      submitted: false,
      loading: false,
      error: '',
      message: ''
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleCheckBoxes = this.handleCheckBoxes.bind(this);
  }
  
  handleChange(e) {
    const { name, value } = e.target;
    this.setState({ [name]: value });
  }

  handleCheckBoxes = (event) => {
    let roles = this.state.roles
    roles.forEach(role => {
       if (role.value === event.target.value)
          role.isChecked =  event.target.checked
    })
    this.setState({roles: roles})
  }

  handleSubmit(e) {
    e.preventDefault();
    this.setState({ submitted: true });
    const { username, password, name, email} = this.state;
    let roles=this.state.roles.filter(role=>role.isChecked).map(role=>role.value);
    // stop here if form is invalid
    if (!(username && password && name && email && roles.length>0)) {
        return;
    }
    this.setState({ loading: true });

    userService.signup(username, password,name, email, roles)
        .then(
            response => {
                console.log(response);
                this.setState({message:response.text});
                const { from } = this.props.location.state || { from: { pathname: "/signup" } };
                this.props.history.push(from);
            },
            error => {console.log(error); this.setState({ error:error , loading: false })}
        );
}


  render () {
    const { username, password, name, email, roles,submitted, loading, error } = this.state;
    return (
      <div disabled={this.state.message} className="SignUpPageWrapper">
      <h2 className="text-center">Register new user</h2>
      {this.state.message?null:
      <form name="form" onSubmit={this.handleSubmit}>
      <div className="form-group">
      <label htmlFor="username">Username: </label>
      <input type="text" className="form-control" name="username" value={username} onChange={this.handleChange} />
      {submitted && !username &&
                            <div className="help-block">Username is required</div>
                        }
      </div>

      <div className="form-group">
      <label htmlFor="password">Password: </label>
      <input type="password" className="form-control" name="password" value={password} onChange={this.handleChange} />
      {submitted && !password &&
                            <div className="help-block">Password is required</div>
                        }
      </div>

      <div className="form-group">
      <label htmlFor="name">Name: </label>
      <input type="text" className="form-control" name="name" value={name} onChange={this.handleChange} />
      {submitted && !name &&
                            <div className="help-block">Name is required</div>
                        }
      </div>

      <div className="form-group">
      <label htmlFor="email">Email: </label>
      <input type="text" className="form-control" name="email" value={email} onChange={this.handleChange} />
      {submitted && !email &&
                            <div className="help-block">Email is required</div>
                        }
      </div>

      <div>
      <div className="form-group">
      <label htmlFor="admin">Admin: </label>
      <input type="checkbox" className="form-control" name="admin" value="ROLE_ADMIN" onChange={this.handleCheckBoxes} />
      </div>
      <div className="form-group">
      <label htmlFor="admin">Guest: </label>
      <input type="checkbox" className="form-control" name="guest" value="ROLE_GUEST" onChange={this.handleCheckBoxes} />
      </div>
      {submitted && (!roles[0].isChecked && !roles[1].isChecked)  &&
                            <div className="help-block">Check at least one role</div>
                        }
      </div>

      <div className="form-group">
                        <button className="btn btn-primary" disabled={loading}>SignUp</button>            
      </div>
      {error && <div className={'alert alert-danger'}>{error}</div>
                    }
      </form> }
      {this.state.message?this.state.message:null}
      <br></br>
      <Link to="/login">Log In</Link></div>
    );
  }
}

export default SignUpPage;
