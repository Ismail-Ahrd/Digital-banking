export interface AccountDetails {
  accountId: string;
  balance: number;
  currentPage: number;
  totalPages: number;
  pageSize: number;
  accountOperationDTOs: AccountOperationDTO[];
}

export interface AccountOperationDTO {
  id: number;
  operationDate: Date;
  amount: number;
  operationType: string;
  description: string;
}
