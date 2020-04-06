import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-billing',
  templateUrl: './billing.component.html',
  styleUrls: ['./billing.component.css']
})
export class BillingComponent implements OnInit {
  
  viewAddress = true;
  viewSuccess=false;

  constructor() { }

  ngOnInit(): void {
  }

}
