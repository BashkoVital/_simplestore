import {Component, OnInit} from '@angular/core';
import {HttpGoodsService} from "../service/http.goodsService";
import {FormBuilder, FormGroup} from "@angular/forms";
import {MatDialog} from '@angular/material/dialog';
import {DialogGoodsComponent} from "./dialog/dialog-goods.component";
import {MatTableDataSource} from "@angular/material/table";

@Component({
  selector: 'goods-comp',
  templateUrl: './goods.component.html',
  providers: [HttpGoodsService],
  styleUrls: ['./goods.component.css']
})
export class GoodsComponent implements OnInit {

  listOfGoods !: MatTableDataSource<any>;
  updateGoodsForm: FormGroup;
  displayedColumns: string[] = ['name', 'price', 'action'];

  constructor(private dialog: MatDialog, private fb: FormBuilder, private httpGoodsService: HttpGoodsService) {
  }

  openDialog() {
    this.dialog.open(DialogGoodsComponent, {
      width: '30%'
    }).afterClosed().subscribe(val => {
      if (val === 'save') {
        this.getAllGoods();
      }
    });
  }

  ngOnInit(): void {
    this.getAllGoods()
  }

  getAllGoods() {
    this.httpGoodsService.getAllGoodsData().subscribe({
      next: (res) => {
        console.log(res);
        this.listOfGoods = new MatTableDataSource<any>(res);
      },
      error: (err) => {
        alert("Error while fetching the Records")
      }
    });
  }

  updateGoods(el: any) {
    this.dialog.open(DialogGoodsComponent, {
      width: '30%',
      data: el
    }).afterClosed().subscribe(val => {
      if (val === 'update') {
        this.getAllGoods();
      }
    });
  }

  deleteGoodsById(id: number) {
    return this.httpGoodsService.deleteGoodsById(id).subscribe({
      next: () => {
        alert("Goods deleted successfully");
        this.getAllGoods();
      },
      error: () => {
        alert("Error while deleting the Goods");
      }
    });
  }
}
