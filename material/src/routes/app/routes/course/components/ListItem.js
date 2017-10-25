import React from 'react';

class ListItem extends React.Component {
  render () {
    return (
      <tr>
        <td className="mdl-data-table__cell--non-numeric">{this.props.number}</td>
        <td className="mdl-data-table__cell--non-numeric">{this.props.courseData.id}</td>
        <td className="mdl-data-table__cell--non-numeric">{this.props.courseData.name}</td>
        <td className="mdl-data-table__cell--non-numeric">{this.props.courseData.description}</td>

        <td className="mdl-data-table__cell--non-numeric" >
          <button
            onClick={()=>{this.props.onDelete(this.props.courseData.id)}}

            type="submit" className="btn btn-primary">Delete</button>
          &nbsp;
          &nbsp;
          <button
            onClick={()=>{this.props.onEdit(this.props.courseData)}}

            type="submit" className="btn btn-primary">Edit</button>
        </td>
      </tr>
    );
  }
}

export default ListItem;
