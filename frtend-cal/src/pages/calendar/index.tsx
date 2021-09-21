import React from 'react'
import { Inject, ScheduleComponent, Day, Week, Month, Agenda,
         EventSettingsModel } from '@syncfusion/ej2-react-schedule';

export default class Calendar extends React.Component {
  private localData: EventSettingsModel = {
    dataSource: [{
    }]
  };

  public render() {
    return <ScheduleComponent currentView='Month' eventSettings={this.localData}>
      <Inject services={[Day, Week, Month, Agenda]} />
    </ScheduleComponent>
  }
}
