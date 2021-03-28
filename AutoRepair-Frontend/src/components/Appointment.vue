  <template>
    <div id="appointment">
      <section class="ftco-section" style="background-image: url(../static/images/bg_3.jpg); background-size:cover;">
        <div class="overlay"></div>
        <div class="container">
          <div class="row d-md-flex justify-content-end">
            <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
              <div class="form-group" style="background-color:rgba(255,165,0,0.7); height:350px; width: 600px; text-align: center; border-radius:25px; padding:20px">
                <h2 >Book An Appointment!</h2>
                <form action="#" class="appointment">
                  <div class="row">
                    <div class="col-md-12">
                      <div class="form-group">
                        <div class="form-field">
                          <div class="select-wrap">
                            <div class="icon"><span class="fa fa-chevron-down"></span></div>
                            <select name="" id="" class="form-control" v-model="selectedService">
                                 <option value="" disabled selected>Select Service</option>
                                  <option v-for="service in services" >
                                    {{ service.name }}
                                  </option>
                            </select>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="form-group">
                        <div class="input-wrap">
                          <input type="date" class="form-control appointment_date" placeholder="Date">
                        </div>
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="form-group">
                        <div class="input-wrap">
                          <input type="time" class="form-control appointment_time" placeholder="Time">
                        </div>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="form-group">
                        <input type="submit" value="Book" class="btn btn-dark py-3 px-4">
                      </div>
                    </div>
                    <div class="error-message">
                      <p>
                         <span v-if="errorAppointment" style="color:red; padding-left: 25px;"> Error: {{errorAppointment}} </span>
                      </p>
                    </div>
                  </div>
                </form>
             </div>
            </div>
          </div>
        </div>
      </section>
      <div style="background-color:#262626;">
        <br>
        <br>
        <br>
      </div>

      <section class="ftco-section" style="background-image: url(../static/images/work-2.jpg);  background-size:cover;">
        <div class="overlay"></div>
        <div class="container">
            <div class="form-group" style="background-color:rgba(255,165,0,0.7); height:450px; width: 600px; text-align: center; border-radius:25px; padding:20px; margin:center;">
              <h2 class="mb-4">Update Your Appointment!</h2>
              <form action="#" class="appointment">
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <div class="form-field">
                        <div class="select-wrap">
                          <div class="icon"><span class="fa fa-chevron-down"></span></div>
                           <select name="appointmentSelect" id="appointmentSelect" class="form-control" v-model="selectedAppointment">
                             <option value="" disabled selected>Select An Appointment</option>
                              <option v-for="appointment in appointments" >
                                {{ appointment.toString() }}
                              </option>
                           </select>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-12">
                    <div class="form-group">
                      <div class="form-field">
                        <div class="select-wrap">
                          <div class="icon"><span class="fa fa-chevron-down"></span></div>
                          <select name="" id="" class="form-control" v-model="selectedService">
                               <option value="" disabled selected>Select A New Service</option>
                                <option v-for="service in services" >
                                  {{ service.name }}
                                </option>
                          </select>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <div class="form-group">
                      <div class="input-wrap">
                        <input type="date" class="form-control appointment_date" placeholder="Date">
                      </div>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <div class="form-group">
                      <div class="input-wrap">
                        <input type="time" class="form-control appointment_time" placeholder="Time">
                      </div>
                    </div>
                  </div>
                  <div class="col-md-12">
                    <div class="form-group">
                      <input type="submit" value="Update" class="btn btn-dark py-3 px-4">
                    </div>
                  </div>
                  <div class="error-message">
                    <p>
                       <span v-if="errorUpdateAppointment" style="color:red; padding-left: 25px;">Error: {{errorUpdateAppointment}} </span>
                    </p>
                  </div>
                </div>
              </form>
            </div>
        </div>
      </section>
       <div style="background-color:#262626;">
        <br>
        <br>
        <br>
      </div>
      <section class="ftco-section" style="background-image: url(../static/images/work-1.jpg);  height:667px; background-size:cover;">
        <div class="overlay"></div>
        <div class="container">
            <div class="form-group" style="background-color:rgba(255,165,0,0.7); height:280px; width: 600px; text-align: center; border-radius:25px; padding:20px; ">
              <h2>Cancel Your Appointment!</h2>
              <form action="#" class="appointment">
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <div class="form-field">
                        <div class="select-wrap">
                          <div class="icon"><span class="fa fa-chevron-down"></span></div>
                           <select name="appointmentSelect" id="appointmentSelect" class="form-control" v-model="selectedAppointment">
                             <option value="" disabled selected>Select An Appointment</option>
                              <option v-for="appointment in appointments" >
                                {{ appointment.toString() }}
                              </option>
                           </select>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-12">
                    <div class="form-group">
                      <input type="submit" value="Cancel" class="btn btn-dark py-3 px-4">
                    </div>
                  </div>
                  <div class="error-message">
                    <p>
                       <span v-if="errorCancelAppointment" style="color:red; padding-left: 25px;">Error: {{errorCancelAppointment}} </span>
                    </p>
                  </div>
                </div>
              </form>
            </div>
        </div>
      </section>
      <br>

      <div>
        <h2 style="text-align:center;"> My Appointments </h2>
        <table style="width:80% ;margin-left:auto;margin-right:auto; border:1px solid black; border-collapse:collapse; table-layout:fixed; text-align:center">
          <thead>
            <tr style="background-color: rgba(255,165,0,0.8);">
              <th style="border:3px solid orange;">Service</th>
              <th style="border:3px solid orange;">Date </th>
              <th style="border:3px solid orange;">Time </th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="appointment in appointments" style="background-color: rgba(255,165,0,0.5);">
              <td style="border:1px solid orange; background-color: rgba(255,165,0,0.2);">{{ appointment.chosenService.toString()}}</td>
              <td style="border:1px solid orange; background-color: rgba(255,165,0,0.2);">{{ appointment.timeSlot.startDate.toString()}}</td>
              <td style="border:1px solid orange; background-color: rgba(255,165,0,0.2);">{{ appointment.timeSlot.startTime.toString()}} - {{ appointment.timeSlot.endTime.toString()}}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <br>
      <br>
      <br>

      <div>
        <h2 style="text-align:center;">Time Slots </h2>
        <table style="width:60% ;margin-left:auto;margin-right:auto; text-align:center;">
          <tr>
            <td>
              <table style="width:100% ;margin-left:auto;margin-right:auto; border:3px solid green; border-collapse:collapse">
                <tr>
                  <th style="border:3px solid green; background-color: rgba(0,128,0,0.4);"> Available Time Slots </th>
                </tr>
                <tr style="background-color: rgba(0,128,0,0.2);">
                  <td style="border:1px solid green;"> 12:00-1:00 </td>
                </tr>
                <tr v-for="availableTimeSlot in availableTimeSlots" style="background-color: rgba(0,128,0,0.2);">
                  <td style="border:1px solid green;">{{ availableTimeSlot.startTime}} - {{ availableTimeSlot.endTime}}</td>
                </tr>
              </table>
            </td>
            <td>
              <table style="width:100% ;margin-left:auto;margin-right:auto; border:3px solid red; border-collapse:collapse"">
                <tr>
                  <th style="border:3px solid red; background-color: rgba(255,0,0,0.4);"> Unavailable Time Slots </th>
                </tr>
                <tr v-for="unavailableTimeSlot in unavailableTimeSlots">
                  <td style="border:1px solid red;">{{ unavailableTimeSlot.startTime.toString()}} - {{ unavailableTimeSlot.endTime}}</td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </div>
      <br>
      <br>
      <br>
    </div>
  </template>

  <script src="./js/appointment.js"></script>
