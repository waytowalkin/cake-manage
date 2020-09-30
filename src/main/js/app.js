const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');
const follow = require('./follow');
const root = '/api';

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {cakes: [],attributes: [], pageSize:1,links:{}};
		this.updatePageSize = this.updatePageSize.bind(this);
		this.onCreate = this.onCreate.bind(this);
		this.onDelete = this.onDelete.bind(this);
	}

	
	loadFromServer(pageSize) {
		follow(client, root, [
			{rel: 'cakes', params: {size: pageSize}}]
		).then(cakeCollection => {
			return client({
				method: 'GET',
				path: cakeCollection.entity._links.profile.href,
				headers: {'Accept': 'application/schema+json'}
			}).then(schema => {
				
				Object.keys(schema.entity.properties).forEach(function (property) {
					if (schema.entity.properties[property].hasOwnProperty('format') &&
						schema.entity.properties[property].format === 'uri') {
						delete schema.entity.properties[property];
					}
					else if (schema.entity.properties[property].hasOwnProperty('$ref')) {
						delete schema.entity.properties[property];
					}
				});
				
				
				this.schema = schema.entity;
				this.links = cakeCollection.entity._links;
				return cakeCollection;
			});
		}).then(cakeCollection => {
			this.setState({
				cakes: cakeCollection.entity._embedded.cakes,
				attributes: Object.keys(this.schema.properties),
				pageSize: pageSize,
				links: cakeCollection.entity._links});
		});
	}
	
	// tag::create[]
	onCreate(newCake) {
		follow(client, root, ['cakes']).then(cakeCollection => {
			return client({
				method: 'POST',
				path: cakeCollection.entity._links.self.href,
				entity: newCake,
				headers: {'Content-Type': 'application/json'}
			})
		}).then(response => {
			return follow(client, root, [
				{rel: 'cakes', params: {'size': this.state.pageSize}}]);
		}).then(response => {
			if (typeof response.entity._links.last !== "undefined") {
				this.onNavigate(response.entity._links.last.href);
			} else {
				this.onNavigate(response.entity._links.self.href);
			}
		});
	}
	// end::create[]

	// tag::delete[]
	onDelete(cake) {
		client({method: 'DELETE', path: cake._links.self.href}).then(response => {
			this.loadFromServer(this.state.pageSize);
		});
	}
	// end::delete[]
	
	// tag::navigate[]
	onNavigate(navUri) {
		client({method: 'GET', path: navUri}).then(cakeCollection => {
			this.setState({
				cakes: cakeCollection.entity._embedded.cakes,
				attributes: this.state.attributes,
				pageSize: this.state.pageSize,
				links: cakeCollection.entity._links
			});
		});
	}
	// end::navigate[]
	
	// tag::update-page-size[]
	updatePageSize(pageSize) {
		if (pageSize !== this.state.pageSize) {
			this.loadFromServer(pageSize);
		}
	}
	
	// tag::follow[]
	componentDidMount() {
		this.loadFromServer(this.state.pageSize);
	}
	// end::follow[]
	
	render() {
		return (
		    <div>
		    <CreateDialog attributes={this.state.attributes} onCreate={this.onCreate}/>
			<CakesList cakes={this.state.cakes}
		    links={this.state.links}
			pageSize={this.state.pageSize}
			onNavigate={this.onNavigate}
			onDelete={this.onDelete}
			updatePageSize={this.updatePageSize}/>
		    </div> 
		)
	}
}


//tag::create-dialog[]
class CreateDialog extends React.Component {

	constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleSubmit(e) {
		e.preventDefault();
		const newCake = {};
		this.props.attributes.forEach(attribute => {
			newCake[attribute] = ReactDOM.findDOMNode(this.refs[attribute]).value.trim();
		});
		this.props.onCreate(newCake);

		// clear out the dialog's inputs
		this.props.attributes.forEach(attribute => {
			ReactDOM.findDOMNode(this.refs[attribute]).value = '';
		});

		// Navigate away from the dialog to hide it.
		window.location = "#";
	}
	render() {
		const inputs = this.props.attributes.map(attribute =>
			<p key={attribute}>
				<input type="text" placeholder={attribute} ref={attribute} className="field"/>
			</p>
		);

		return (
			<div>
				<div id="createCake" className="modalDialog">
					<div>						
						<h2>Create new cake :</h2>
						<form>
							{inputs}
							<button onClick={this.handleSubmit}>Create</button>
						</form>
						
					</div>
				</div>
			</div>
		)
	}

}
// end::create-dialog[]

class CakesList extends React.Component{
	render() {
		const  cakes = this.props.cakes.map(cake =>
			<Cake key={cake._links.self.href} cake={cake} onDelete={this.props.onDelete}/>
		);
		return (
			<div>
			<h2>List Of Cakes : </h2>					
			<table>
				<tbody>
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Description</th>
						<th>Created by</th>
						<th></th>
					</tr>
					{cakes}
				</tbody>
			</table>
			</div>
		)
	}
}




class Cake extends React.Component{
	
	constructor(props) {
		super(props);
		this.handleDelete = this.handleDelete.bind(this);
	}

	handleDelete() {
		this.props.onDelete(this.props.cake);
	}

	render() {
		return (
			<tr>
				<td>{this.props.cake.firstName}</td>
				<td>{this.props.cake.lastName}</td>
				<td>{this.props.cake.description}</td>
				<td>{this.props.cake.manager.name}</td>

				<td>
				<button onClick={this.handleDelete}>Delete</button>
			    </td>
			</tr>
		)
	}
}
// end::employee[]

// tag::render[]
ReactDOM.render(
	<App />,
	document.getElementById('react')
)