import React, { PureComponent } from 'react';
import { userService } from './user.service';
import { Link } from 'react-router-dom';

class LoginPage extends React.Component { 
  constructor(props) {
    super(props);

    userService.logout();

    this.state = {
      username: '',
      password: '',
      submitted: false,
      loading: false,
      error: '',
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(e) {
    const { name, value } = e.target;
    this.setState({ [name]: value });
  }

  handleSubmit(e) {
    e.preventDefault();
    this.setState({ submitted: true });
    const { username, password} = this.state;
    // stop here if form is invalid
    if (!(username && password)) {
        return;
    }
    this.setState({ loading: true });
    userService.login(username, password)
        .then(
            user => {
                console.log(user+"245");
                const { from } = this.props.location.state || { from: { pathname: "/orders" } };
                this.props.history.push(from);
            },
            error => this.setState({ error:error, loading: false })
        );
}

  render () {
    const { username, password, submitted, loading, error } = this.state;
    return (
      <div className="LoginPageWrapper">
      <h2 className="text-center">Login</h2>
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
                        <button className="btn btn-primary" disabled={loading}>Login</button>            
      </div>
      {error && <div className={'alert alert-danger'}>{error}</div>
                    }
      </form>
      <br></br>
      <Link to="/signup">SignUp</Link>
      </div>
    );
  }
}

export default LoginPage;
