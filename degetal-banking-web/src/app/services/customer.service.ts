import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Customer} from "../model/customer.model";
import {environment} from "../../environments/environment.development";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  backendHost: string="http://localhost:8080";

  constructor(private http:HttpClient) { }

  public getCustomers() :Observable<Customer []> {
    return this.http.get<Customer []>(`${environment.backendHost}/customers`);
  }
  public searchCustomers(kw: string): Observable<Customer []> {
    return this.http.get<Customer []>(`${environment.backendHost}/customers/search?keyword=${kw}`);
  }

  public saveCustomer(customer: Customer): Observable<Customer> {
    return this.http.post<Customer>(`${environment.backendHost}/customers`, customer);
  }

  public deleteCustomer(customer: Customer) {
    return this.http.delete(`${environment.backendHost}/customers/${customer.id}`);
  }

}
