import React from 'react';

class ListItem extends React.Component {
  render () {
    return (
          <tr>
            <td className="mdl-data-table__cell--non-numeric">{this.props.locationData.name}</td>
            <td className="mdl-data-table__cell--non-numeric">{this.props.locationData.address}</td>
            <td className="mdl-data-table__cell--non-numeric" >{this.props.locationData.alias}</td>
            <td className="mdl-data-table__cell--non-numeric" >
              <button
                onClick={()=>{this.props.onDelete(this.props.locationData.id)}}

                type="submit" className="btn btn-primary">Delete</button>
            </td>
            <td>
              <button
                onClick={()=>{this.props.onEdit(this.props.locationData)}}

                type="submit" className="btn btn-primary">Edit</button>
            </td>
          </tr>
          );
  }
}

export default ListItem;
