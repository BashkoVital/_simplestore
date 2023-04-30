import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HttpOrderService} from "../../service/http.orderService";
import {MatDialogRef, MAT_DIALOG_DATA} from "@angular/material/dialog"


@Component({
  selector: 'app-dialog-order',
  templateUrl: './dialog-order.component.html',
  providers: [HttpOrderService],
  styleUrls: ['./dialog-order.component.css']
})
export class DialogOrderComponent implements OnInit {

  addOrderForm !: FormGroup
  actionBtn: string = "Сохранить";

  constructor(private fb: FormBuilder,
              private httpOrdersService: HttpOrderService,
              @Inject(MAT_DIALOG_DATA) public editData: any,
              private dialogRef: MatDialogRef<DialogOrderComponent>) {
  }


  ngOnInit(): void {
    this.addOrderForm = this.fb.group({
      idOrder: [''],
      client: ['', Validators.required],
      date: ['', Validators.required],
      address: ['', Validators.required]
    });
    if (this.editData) {
      this.actionBtn = "Редактировать";
      this.addOrderForm.controls['client'].setValue(this.editData.client);
      this.addOrderForm.controls['date'].setValue(this.editData.date);
      this.addOrderForm.controls['address'].setValue(this.editData.address);
    }
  }

  addOrder(): void {
    if (!this.editData) {
      if (this.addOrderForm.valid) this.httpOrdersService.addOrder(this.addOrderForm.value)
        .subscribe({
          next: (res) => {
            alert("Order added successfully")
            this.addOrderForm.reset();
            this.dialogRef.close('save');
          },
          error: () => {
            alert("Error while adding the order")
          }
        });
    } else {
      this.updateOrder();
    }
  }

  updateOrder() {
    this.httpOrdersService.updateOrderById(this.addOrderForm.value, this.editData.idOrder).subscribe({
      next: (res) => {
        alert("Order updated successfully");
        this.addOrderForm.reset();
        this.dialogRef.close('update');
      },
      error: () => {
        alert("Error while updating the record")
      }
    });
  }
}
