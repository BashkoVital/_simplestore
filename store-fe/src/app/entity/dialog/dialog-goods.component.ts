import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HttpGoodsService} from "../../service/http.goodsService";
import {GoodsComponent} from "../goods.component";
import {MatDialogRef, MAT_DIALOG_DATA} from "@angular/material/dialog"

@Component({
  selector: 'app-dialog-goods',
  templateUrl: './dialog-goods.component.html',
  providers: [HttpGoodsService],
  styleUrls: ['./dialog-goods.component.css']
})
export class DialogGoodsComponent implements OnInit {

  addGoodsForm !: FormGroup;
  goodsComponent: GoodsComponent;
  actionBtn: string = "Сохранить";

  constructor(private fb: FormBuilder,
              private httpGoodsService: HttpGoodsService,
              @Inject(MAT_DIALOG_DATA) public editData: any,
              private dialogRef: MatDialogRef<DialogGoodsComponent>) {

  }

  ngOnInit(): void {
    this.addGoodsForm = this.fb.group({
      idGoods: [''],
      name: ['', Validators.required],
      price: ['', Validators.required]
    });

    if (this.editData) {
      this.actionBtn = "Редактировать";
      this.addGoodsForm.controls['name'].setValue(this.editData.name);
      this.addGoodsForm.controls['price'].setValue(this.editData.price);
    }
  }

  addGoods(): void {
    if (!this.editData) {
      if (this.addGoodsForm.valid) this.httpGoodsService.addGoods(this.addGoodsForm.value)
        .subscribe({
          next: (res) => {
            alert("Goods added successfully")
            this.addGoodsForm.reset();
            this.dialogRef.close('save');
          },
          error: () => {
            alert("Error while adding the goods")
          }
        });
    } else {
      this.updateGoods();
    }
  }

  updateGoods() {
    this.httpGoodsService.updateGoodsById(this.addGoodsForm.value, this.editData.idGoods).subscribe({
      next: (res) => {
        alert("Goods updated successfully")
        this.addGoodsForm.reset();
        this.dialogRef.close('update');
      },
      error: () => {
        alert("Error while updating the record")
      }
    });
  }
}
