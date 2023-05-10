import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Customer} from "../../model/customer.model";
import {AccountsService} from "../../services/accounts.service";
import {catchError, Observable, throwError} from "rxjs";
import {AccountDetails} from "../../model/account.model";

@Component({
  selector: 'app-customer-accounts',
  templateUrl: './customer-accounts.component.html',
  styleUrls: ['./customer-accounts.component.css']
})
export class CustomerAccountsComponent implements OnInit{
  customerId!: string;
  customer!: Customer;
  account$!: Observable<any>;
  errorMessage!: string;

  constructor(private route: ActivatedRoute, private router: Router, private accountService: AccountsService) {
    this.customer = this.router.getCurrentNavigation()?.extras.state as Customer;
  }

  ngOnInit(): void {
    this.customerId = this.route.snapshot.params['id'];
    this.handleGetAllAccounts();
    this.accountService.getAccountsByCustomerId(this.customerId, 0, 5).subscribe({
      next: data => {
        console.log(data);
      }
    })
  }

  handleGetAllAccounts() {
    console.log("Hello")
    this.account$ = this.accountService.getAccountsByCustomerId(this.customerId, 0, 5).pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(err);
      })
    );
  }

  goToPage(page: number) {
  }

  goToOperations(accountId: string) {
    this.router.navigateByUrl("/accounts/"+accountId, {state: {accountId}});
  }
}
