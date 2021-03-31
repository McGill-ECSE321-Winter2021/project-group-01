  <template>
    <div id="appointment">
      <nav class="navbar navbar-expand-lg navbar-light ftco_navbar bg-light ftco-navbar-light" id="ftco-navbar">
        <div class="container">
          <button
            class="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#ftco-nav"
            aria-controls="ftco-nav"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span class="fa fa-bars"></span> Menu
          </button>
          <form action="#" class="searchform order-lg-last">
            <div class="form-group d-flex">
              <input type="text" class="form-control pl-3" placeholder="Search" />
              <button type="submit" placeholder="" class="form-control search">
                <span class="fa fa-search"></span>
              </button>
            </div>
          </form>
          <div class="collapse navbar-collapse" id="ftco-nav">
            <ul class="navbar-nav mr-auto">
              <li class="nav-item active">
                <router-link class="nav-link" to="/">Home</router-link>
              </li>
              <li class="nav-item">
                <router-link class="nav-link" to="/services"
                  >Services</router-link
                >
              </li>
              <li class="nav-item">
                <router-link class="nav-link" to="/appointments"
                  >Appointments</router-link
                >
              </li>
              <li class="nav-item">
                <router-link class="nav-link" to="/account">Account</router-link>
              </li>
              <li class="nav-item">
                <router-link class="nav-link" to="/logout">Logout</router-link>
              </li>
            </ul>
          </div>
        </div>
      </nav>
      <section class="ftco-section" style="background-image: url(../static/images/bg_3.jpg); height:667px; background-size:cover;">
        <div class="overlay"></div>
        <div class="container">
          <div class="row d-md-flex justify-content-end">
            <div class="col-md-12 col-lg-6 half p-3 py-5 pl-lg-5">
              <div class="form-group" style="background-color:rgba(255,165,0,0.7); height:350px; width: 600px; text-align: center; border-radius:25px; padding:20px">
                <h2 >Book An Appointment!</h2>
                <form action="#/appointments" class="appointment">
                  <div class="row">
                    <div class="col-md-12">
                      <div class="form-group">
                        <div class="form-field">
                          <div class="select-wrap">
                            <div class="icon"><span class="fa fa-chevron-down"></span></div>
                            <select name="serviceName" id="" class="form-control" v-model="serviceName">
                                 <option value="">Select Service</option>
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
                          <input type="date" class="form-control appointment_date" placeholder="Date" v-model="appointmentDate" name="appointmentDate"
                          @change="getAvailableTimeSlots(appointmentDate.toString())">
                        </div>
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="form-group">
                        <div class="input-wrap">
                          <input type="time" class="form-control appointment_time" placeholder="Time" v-model="appointmentTime" name="appointmentTime">
                        </div>
                      </div>
                    </div>
                    <div class="col-md-12">
                      <div class="form-group">
                        <button type="button"
                          class="btn btn-dark py-3 px-4"
                          @click="makeAppointment(tamara,appointmentDate.toString(),appointmentTime.toString(),serviceName), getAvailableTimeSlots(appointmentDate.toString()),
                          getAppointmentsOfCustomer(bob)">Book
                        </button>
                      </div>
                    </div>
                    <div class="form-group">
                        <h5 v-if="errorMakeAppointment" style="color:red; padding-left: 25px;"> Error: {{errorMakeAppointment}} </h5>
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
              <form action="#/appointments" class="appointment">
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <div class="form-field">
                        <div class="select-wrap">
                          <div class="icon"><span class="fa fa-chevron-down"></span></div>
                           <select name="selectedAppointment" id="" class="form-control" v-model="selectedAppointment">
                             <option value="">Select An Appointment</option>
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
                          <select name="newServiceName" id="" class="form-control" v-model="newServiceName">
                               <option value="">Select A New Service</option>
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
                        <input type="date" class="form-control appointment_date" placeholder="Date" v-model="newAppointmentDate" name="newAppointmentDate"
                        @change="getAvailableTimeSlots(newAppointmentDate.toString())">
                      </div>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <div class="form-group">
                      <div class="input-wrap">
                        <input type="time" class="form-control appointment_time" placeholder="Time" v-model="newAppointmentTime" name="newAppointmentTime">
                      </div>
                    </div>
                  </div>
                  <div class="col-md-12">
                    <div class="form-group">
                      <button type="button"
                        class="btn btn-dark py-3 px-4"
                        @click="updateAppointment(tamara,selectedAppointment,newAppointmentTime.toString(),newAppointmentDate.toString(), newServiceName)">Update
                      </button>
                    </div>
                  </div>
                  <div class="form-group">
                    <h5 v-if="errorUpdateAppointment" style="color:red; padding-left: 25px;"> Error: {{errorUpdateAppointment}} </h5>
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
              <form action="#/appointments" class="appointment">
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <div class="form-field">
                        <div class="select-wrap">
                          <div class="icon"><span class="fa fa-chevron-down"></span></div>
                           <select name="selectedAppointment" id="" class="form-control" v-model="selectedAppointment">
                             <option value="">Select An Appointment</option>
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
                      <button type="button"
                        class="btn btn-dark py-3 px-4"
                        @click="getAppointmentsOfCustomer(username),cancelAppointment(tamara,selectedAppointment)">Cancel
                      </button>
                    </div>
                  </div>
                  <div class="form-group">
                     <h5 v-if="errorCancelAppointment" style="color:red; padding-left: 25px;"> Error: {{errorCancelAppointment}} </h5>
                  </div>
                </div>
              </form>
            </div>
        </div>
      </section>
      <br>

      <div class="split left">
        <div class="centered">
            <div>
              <h2 style="text-align:center;"> My Appointments </h2>
              <table style="width:60% ;margin-left:auto;margin-right:auto; border:1px solid black; border-collapse:collapse; table-layout:fixed; text-align:center">
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
        </div>
      </div>
      <br>
      <br>
      <br>


      <div class="split right">
        <div class="centered">
          <h2 style="text-align:center;">Time Slots </h2>
          <table style="width:30% ;margin-left:auto;margin-right:auto; border:3px solid green; border-collapse:collapse; text-align:center;">
            <tr>
              <th style="border:3px solid green; background-color: rgba(0,128,0,0.4);"> Available Time Slots </th>
            </tr>
            <tr v-for="availableTimeSlot in availableTimeSlots" style="background-color: rgba(0,128,0,0.2);">
              <td style="border:1px solid green;">{{ availableTimeSlot.startTime}} - {{ availableTimeSlot.endTime}}</td>
            </tr>
          </table>
        </div>
      </div>


       <div>
        <h2> {{appointments}} </h2>
       </div>
      <br>
      <br>
      <br>
    </div>
  </template>

  <script src="./js/appointment.js"></script>


