export class DatajsonModule {

  theirReferenceId: string = '';
  city: string = '';
  customerName: string = '';
  amount: number = 0;
  currency: string = '';

  constructor(
    theirReferenceId: string,
    city: string,
    customerName: string,
    amount: number,
    currency: string
  ) {
    this.theirReferenceId = theirReferenceId;
    this.city = city;
    this.customerName = customerName;
    this.amount = amount;
    this.currency = currency;
  }
}
