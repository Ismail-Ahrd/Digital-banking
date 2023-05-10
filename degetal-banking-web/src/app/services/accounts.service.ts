import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment.development";
import {Observable} from "rxjs";
import {AccountDetails} from "../model/account.model";

@Injectable({
  providedIn: 'root'
})
export class AccountsService {

  constructor(private http: HttpClient) { }

  public getAccount(accountId: string, page: number, size: number): Observable<AccountDetails> {
    let url = `${environment.backendHost}/accounts/${accountId}/pageOperations?page=${page}&size=${size}`;
    return this.http.get<AccountDetails>(url);
  }

  public getAccountsByCustomerId(customerId: string, page: number, size: number) {
    let url = `${environment.backendHost}/accounts/customer/${customerId}?page=${page}&size=${size}`;
    return this.http.get(url);
  }

  public debit(accountId: string, amount: number, description: string) {
    let url = `${environment.backendHost}/operations/debit`;
    let data = { accountId, amount, description };
    return this.http.post(url, data);
  }

  public credit(accountId: string, amount: number, description: string) {
    let url = `${environment.backendHost}/operations/credit`;
    let data = { accountId, amount, description };
    return this.http.post(url, data);
  }

  public transfer(accountIdSource: string, accountIdDestination: string,
                  amount: number, description: string) {
    let url = `${environment.backendHost}/operations/transfer`;
    let data = { accountIdSource, accountIdDestination, amount, description };
    return this.http.post(url, data);
  }

}
