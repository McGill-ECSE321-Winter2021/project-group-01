<template>
  <div id="MakeAppointment">
    <h2> Book an Appointment! </h2>

    <table style="width:50% ;margin-left:auto;margin-right:auto;">
      <tr>
        <th>
          <label>Service:
            <select v-model="selectedService">
                 <option disabled value="">Please select one</option>
                  <option v-for="service in services" >
                    {{ service.name }}
                  </option>
            </select>
          </label>
        </th>

        <th>
          <label> Start Date: </label>
          <input type="date" name="Start Date" v-model="newAppointment.appointmentDate" placeholder="YYYY-MM-DD"  v-on:click="getAvailableTimeSlots(newAppointment.appointmentDate)">
        </th>

        <th>
          <label> Start Time: </label>
          <input type="time" name="Start Time" v-model="newAppointment.appointmentTime" placeholder="HH:mm">
        </th>
      </tr>
    </table>
    <br>
    <p>
      <span v-if="errorService" style="color:red">Error: {{errorService}} </span>
    </p>
    <button type="button" v-on:click="makeAppointment(loggedInUser,newAppointment.appointmentDate, newAppointment.appointmentTime, selectedService)">Book</button>
    <p>
       <span v-if="errorAppointment" style="color:red">Error: {{errorAppointment}} </span>
    </p>

    <br>
    <br>
    <br>
    <h3> View TimeSlots </h3>

    <table style="width:60% ;margin-left:auto;margin-right:auto;">
      <tr>
        <td>
          <table style="width:100% ;margin-left:auto;margin-right:auto; border:3px solid green; border-collapse:collapse">
            <tr>
              <th> Available Time Slots </th>
            </tr>
            <tr v-for="availableTimeSlot in availableTimeSlots">
              <td style="border:3px solid green;">{{ availableTimeSlot.startTime}} - {{ availableTimeSlot.endTime}}</td>
            </tr>
          </table>
        </td>
        <td>
          <table style="width:100% ;margin-left:auto;margin-right:auto; border:3px solid red; border-collapse:collapse"">
            <tr>
              <th> Unavailable Time Slots </th>
            </tr>
            <tr v-for="unavailableTimeSlot in unavailableTimeSlots">
              <td style="border:3px solid red;">{{ unavailableTimeSlot.startTime.toString()}} - {{ unavailableTimeSlot.endTime}}</td>
            </tr>
          </table>
        </td>
      </tr>
    </table>

    <br>
    <br>
    <br>
    <h3> My Appointments </h3>

    <table style="width:60% ;margin-left:auto;margin-right:auto; border:1px solid black; border-collapse:collapse">
      <tr>
        <th style="border:1px solid black;">Service</th>
        <th style="border:1px solid black;">Date </th>
        <th style="border:1px solid black;">Time </th>
      </tr>
      <tr v-for="appointment in appointments">
        <td style="border:1px solid black;">{{ appointment.chosenService.toString()}}</td>
        <td style="border:1px solid black;">{{ appointment.timeSlot.startDate.toString()}}</td>
        <td style="border:1px solid black;">{{ appointment.timeSlot.startTime.toString()}} - {{ appointment.timeSlot.endTime.toString()}}</td>
      </tr>
    </table>
  </div>
</template>

<script src="./js/makeAppointment.js"></script>


 <style scoped>
 h1, h2 {
   font-weight: normal;
 }

 ul {
   list-style-type: none;
   padding: 0;
 }

 li {
   display: inline-block;
   margin: 0 10px;
 }

 a {
   color: #42b983;
 }
 </style>
