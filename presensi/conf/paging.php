<?php
function paging ( $sql , $item_per_page , $page_per_block , $url , $page , $block ) {

/*The Simple Advanced Paging by Bimosaurus..
Please make it sure that this page is load the code like it
********************************************
$item_per_page = 10;
$page_per_block= 10;

if (!isset($_GET[page]) ){
   $page  = 1;
   $block = 1;
}else{
   $page  = ($_GET[page]);
   $block = ceil($page/$page_per_block);
}

$offset = ($page - 1) * $item_per_page ;
********************************************
*/

   $q        = bukaquery($sql);
   $n        = mysqli_num_rows($q);
   $jumdata  = $n;
   $jumhal   = ceil ( $n / $item_per_page );
   $jumblock = ceil ( $jumhal / $page_per_block );
   /*Prevent from Paging Hacking*/
   if( $_GET[page] > $jumhal ) {
      $page    = $jumhal;
      $block   = ceil($page/$page_per_block);
   }elseif($_GET[page] < 1){
      $page    = 1;
      $block   = 1;
   }
   /*end of prevention*/
   
   if ( $jumhal>1 )  {
      ?>
      <span style="float:left; margin-right:10px; font-size:13px; color:#333;">Halaman : </span>
      <?php
   
      if (  $block <= 1  )
      {
         $prev = "no";
         $next = "yes";
      }
      elseif (  $block >= $jumblock  )
      {
         $prev = "yes";
         $next = "no";
      }
      else
      {
         $prev = "yes";
         $next = "yes";
      }


      if (  $prev == "yes"  )  {
         $prev_page  = ( ($block - 1) * $page_per_block );
         $prev_block = $block - 1;
         ?>
         <span class="block_nav_page"><a href="<?php echo $url;?>&page=1">&laquo;&laquo;First</a></span>
         <span class="block_nav_page" style="margin-right:8px;"><a href="<?php echo $url;?>&page=<?php echo $prev_page; ?>">&laquo;Prev</a></span>
         <?php
      }  elseif ( ($prev == "no") && ($jumblock>1) )  {
      ?>
         <span class="block_nav_page" style="color:#ccc;">&laquo;&laquo;First</span>
         <span class="block_nav_page" style="margin-right:8px; color:#ccc;">&laquo;Prev</span>
         <?php
      }
   
      $startblock = ((($block - 1) * $page_per_block) + 1);
      $endblock   = ($block * $page_per_block);
   
      while ( (  $startblock <= $endblock  ) && (  $startblock <= $jumhal  ) )  {
         if (  $startblock == $page  ){
            ?>
            <span class="item_paging_current"><?php echo $startblock; ?></span>
            <?php
         } else {
            ?>
            <span class="item_paging_active"><a href="<?php echo $url;?>&page=<?php echo $startblock; ?>"><?php echo $startblock; ?></a></span>
            <?php
         }
         $startblock++;
      }


      if (  $next == "yes" )  {
         $next_page = ($block * $page_per_block) + 1;
         $next_block= $block + 1;
         if ( $next_page <= $jumhal )  {
            ?>
            <span class="block_nav_page" style="margin-left:8px;"><a href="<?php echo $url;?>&page=<?php echo $next_page; ?>">Next&raquo;</a></span>
            <span class="block_nav_page"><a href="<?php echo $url;?>&page=<?php echo $jumhal; ?>">End &raquo; &raquo;</a></span>
            <?php
         }
      }  else  {
         ?>
         <span class="block_nav_page" style="margin-left:8px; color:#ccc;">Next&raquo;</span>
         <span class="block_nav_page" style="color:#ccc;">End&raquo;&raquo;</span>
         <?php
      }
   }
}





/*------------------------------------------------------------------------------------------*/
/*
Simple Advanced Paging...
Author          : bimosaurus
Version         : 1.0
Date Version    : August the 17,2011
Moments         : Indonesia Independence Day
Example:
paging('SELECT * FROM `table`',10,5,'?mod=namamodul&submod=namasubmodul',$page,$block);
For bug and Consultation : bimosaurus@gmail.com

Models : 

<<First <Previous 10 11 12 13 14 15 Next> End>>

*/
/*--------------------------------------------------------------------------------------------*/
?>
