import { Component, OnInit } from '@angular/core';
import { AddressService} from './address.service';
import { Address } from "../../../../../shared/models/address";
import {Customer} from "../../../../../shared/models/customer";
import {CustomerSharedService} from "../../../../../customer/customer-home/customer-shared-service";


@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css']
})
export class AddressComponent implements OnInit {
  loggedInCustomer: Customer;
  address:Address[];
  addressToAdd: Address;
  viewForm: boolean = false;
  successMessage=null;
  errorMessage=null;
  constructor(private customerSharedService: CustomerSharedService, private addressService: AddressService) { }

  ngOnInit(): void {
    this.getLoggedInCustomer();
  }

  getLoggedInCustomer() {
    this.customerSharedService.updatedCustomer.subscribe(customer => this.loggedInCustomer = customer);
    this.loggedInCustomer = JSON.parse(sessionStorage.getItem("customer"));
    this.address= this.loggedInCustomer.addresses;
}
addNewAddress(){
  this.successMessage = null;
  this.errorMessage = null;
  this.addressToAdd = new Address();
  this.viewForm=true;
}

addAddress(){
  this.successMessage = null;
  this.errorMessage = null;
  this.addressService.addNewAddress(this.addressToAdd, this.loggedInCustomer.emailId).subscribe(response => {
      this.successMessage = response;
      let id = this.successMessage.substring(this.successMessage.indexOf(":")+1).trim();
      this.addressToAdd.addressId = parseInt(id);
      this.address.push(this.addressToAdd);
      this.loggedInCustomer.addresses = this.address;
      // this.addressToAdd = null;
      sessionStorage.setItem("customer", JSON.stringify(this.loggedInCustomer));
  }, error => {
      this.errorMessage = error;
  })
}
cancelAdd(){
  this.successMessage = null;
  this.errorMessage = null;
  this.addressToAdd = null;
  this.viewForm=false;
}

}
