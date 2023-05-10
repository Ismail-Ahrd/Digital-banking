import {Component, DoCheck, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {AccountsService} from "../../services/accounts.service";
import {catchError, Observable, throwError} from "rxjs";
import {AccountDetails} from "../../model/account.model";
import {ActivatedRoute, Router} from "@angular/router";

interface Account {
  accountId: string
};

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {
  accountFormGroup!: FormGroup;
  currentPage: number = 0;
  pageSize: number = 5;
  account$!: Observable<AccountDetails>;
  accountId: string='';
  operationFormGroup!: FormGroup;
  errorMessage!: string;

  constructor(private fb: FormBuilder, private accountService: AccountsService, private router: Router,private route: ActivatedRoute) {
    //let id = (this.router.getCurrentNavigation()?.extras.state as Account).accountId;

  }

  ngOnInit(): void {
    this.accountId = this.route.snapshot.params['id'];
    this.accountFormGroup = this.fb.group({
      accountId: this.fb.control(this.route.snapshot.params['id'])
    });



    this.operationFormGroup = this.fb.group({
      operationType: this.fb.control(null),
      amount: this.fb.control(0),
      description: this.fb.control(null),
      accountDestination: this.fb.control(null)
    });
  }




  handleSearchAccount() {
    let accountId = this.accountFormGroup.value.accountId
    this.account$ = this.accountService.getAccount(accountId, this.currentPage, this.pageSize).pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(err);
      })
    );
  }

  goToPage(page: number) {
    this.currentPage = page;
    this.handleSearchAccount();
  }

  handleAccountOperation() {
    let accountId: string = this.accountFormGroup.value.accountId;
    let amount: number = this.operationFormGroup.value.amount;
    let description: string = this.operationFormGroup.value.description;
    let accountDestinationId: string = this.operationFormGroup.value.accountDestination;
    let operationType = this.operationFormGroup.value.operationType;
    if (operationType === "CREDIT") {
      this.accountService.credit(accountId, amount, description).subscribe({
        next: data => {
          alert("Success Credit");
          this.operationFormGroup.reset();
          this.handleSearchAccount();
        },
        error: err => {
          console.log(err);
        }
      });
    } else if (operationType === "DEBIT") {
      this.accountService.debit(accountId, amount, description).subscribe({
        next: data => {
          alert("Success Debit");
          this.operationFormGroup.reset();
          this.handleSearchAccount();
        },
        error: err => {
          console.log(err);
        }
      });
    } else if (operationType === "TRANSFER") {
      this.accountService.transfer(accountId, accountDestinationId, amount, description).subscribe({
        next: data => {
          alert("Success Transfer");
          this.operationFormGroup.reset();
          this.handleSearchAccount();
        },
        error: err => {
          console.log(err);
        }
      });
    }
  }

}
