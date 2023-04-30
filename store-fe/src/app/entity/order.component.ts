import {Component, OnInit} from '@angular/core';
import {HttpOrderService} from "../service/http.orderService";
import {FormBuilder, FormGroup} from "@angular/forms";
import {MatDialog} from '@angular/material/dialog';
import {DialogOrderComponent} from "./dialog/dialog-order.component";
import {MatTableDataSource} from "@angular/material/table";

@Component({
  selector: 'order-comp',
  templateUrl: './order.component.html',
  providers: [HttpOrderService],
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

  listOfOrders !: MatTableDataSource<any>;
  updateOrderForm: FormGroup;
  displayedColumns: string[] = ['client', 'date', 'address', 'action'];

  constructor(private dialog: MatDialog, private fb: FormBuilder, private httpOrdersService: HttpOrderService) {
  }

  openDialog() {
    this.dialog.open(DialogOrderComponent, {
      width: '30%'
    }).afterClosed().subscribe(val => {
      if (val === 'save') {
        this.getAllOrders()
      }
    });
  }

  ngOnInit(): void {
    this.getAllOrders()
  }

  getAllOrders() {
    this.httpOrdersService.getAllOrdersData().subscribe({
      next: (res) => {
        console.log(res);
        this.listOfOrders = new MatTableDataSource<any>(res);
      },
      error: (err) => {
        alert("Error while fetching the Records")
      }
    });
  }

  updateOrder(el: any) {
    this.dialog.open(DialogOrderComponent, {
      width: '30%',
      data: el
    }).afterClosed().subscribe(val => {
      if (val === 'update') {
        this.getAllOrders()
      }
    });
  }

  deleteOrderById(id: number) {
    this.httpOrdersService.deleteOrderById(id)
      .subscribe({
        next: (res) => {
          alert("Order deleted successfully");
          this.getAllOrders()
        }, error: () => {
          alert("Error while deleting the Order");
        }
      });
  }
}

