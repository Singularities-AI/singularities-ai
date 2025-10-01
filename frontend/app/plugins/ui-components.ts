import { Form, Field as FormField } from 'vee-validate'
import { toast } from '@/components/ui/toast'

// Accordion
import Accordion from '@/components/ui/accordion/Accordion.vue'
import AccordionContent from '@/components/ui/accordion/AccordionContent.vue'
import AccordionItem from '@/components/ui/accordion/AccordionItem.vue'
import AccordionTrigger from '@/components/ui/accordion/AccordionTrigger.vue'

// Alert
import Alert from '@/components/ui/alert/Alert.vue'
import AlertDescription from '@/components/ui/alert/AlertDescription.vue'
import AlertTitle from '@/components/ui/alert/AlertTitle.vue'

// Alert Dialog
import AlertDialog from '@/components/ui/alert-dialog/AlertDialog.vue'
import AlertDialogAction from '@/components/ui/alert-dialog/AlertDialogAction.vue'
import AlertDialogCancel from '@/components/ui/alert-dialog/AlertDialogCancel.vue'
import AlertDialogContent from '@/components/ui/alert-dialog/AlertDialogContent.vue'
import AlertDialogDescription from '@/components/ui/alert-dialog/AlertDialogDescription.vue'
import AlertDialogFooter from '@/components/ui/alert-dialog/AlertDialogFooter.vue'
import AlertDialogHeader from '@/components/ui/alert-dialog/AlertDialogHeader.vue'
import AlertDialogTitle from '@/components/ui/alert-dialog/AlertDialogTitle.vue'
import AlertDialogTrigger from '@/components/ui/alert-dialog/AlertDialogTrigger.vue'

// Aspect Ratio
import AspectRatio from '@/components/ui/aspect-ratio/AspectRatio.vue'

// Auto Form
import AutoForm from '@/components/ui/auto-form/AutoForm.vue'
import AutoFormField from '@/components/ui/auto-form/AutoFormField.vue'
import AutoFormFieldArray from '@/components/ui/auto-form/AutoFormFieldArray.vue'
import AutoFormFieldBoolean from '@/components/ui/auto-form/AutoFormFieldBoolean.vue'
import AutoFormFieldDate from '@/components/ui/auto-form/AutoFormFieldDate.vue'
import AutoFormFieldEnum from '@/components/ui/auto-form/AutoFormFieldEnum.vue'
import AutoFormFieldFile from '@/components/ui/auto-form/AutoFormFieldFile.vue'
import AutoFormFieldInput from '@/components/ui/auto-form/AutoFormFieldInput.vue'
import AutoFormFieldNumber from '@/components/ui/auto-form/AutoFormFieldNumber.vue'
import AutoFormFieldObject from '@/components/ui/auto-form/AutoFormFieldObject.vue'
import AutoFormLabel from '@/components/ui/auto-form/AutoFormLabel.vue'

// Avatar
import Avatar from '@/components/ui/avatar/Avatar.vue'
import AvatarFallback from '@/components/ui/avatar/AvatarFallback.vue'
import AvatarImage from '@/components/ui/avatar/AvatarImage.vue'

// Badge
import Badge from '@/components/ui/badge/Badge.vue'

// Breadcrumb
import Breadcrumb from '@/components/ui/breadcrumb/Breadcrumb.vue'
import BreadcrumbEllipsis from '@/components/ui/breadcrumb/BreadcrumbEllipsis.vue'
import BreadcrumbItem from '@/components/ui/breadcrumb/BreadcrumbItem.vue'
import BreadcrumbLink from '@/components/ui/breadcrumb/BreadcrumbLink.vue'
import BreadcrumbList from '@/components/ui/breadcrumb/BreadcrumbList.vue'
import BreadcrumbPage from '@/components/ui/breadcrumb/BreadcrumbPage.vue'
import BreadcrumbSeparator from '@/components/ui/breadcrumb/BreadcrumbSeparator.vue'

// Button
import Button from '@/components/ui/button/Button.vue'

// Calendar
import Calendar from '@/components/ui/calendar/Calendar.vue'
import CalendarCell from '@/components/ui/calendar/CalendarCell.vue'
import CalendarCellTrigger from '@/components/ui/calendar/CalendarCellTrigger.vue'
import CalendarGrid from '@/components/ui/calendar/CalendarGrid.vue'
import CalendarGridBody from '@/components/ui/calendar/CalendarGridBody.vue'
import CalendarGridHead from '@/components/ui/calendar/CalendarGridHead.vue'
import CalendarGridRow from '@/components/ui/calendar/CalendarGridRow.vue'
import CalendarHeadCell from '@/components/ui/calendar/CalendarHeadCell.vue'
import CalendarHeader from '@/components/ui/calendar/CalendarHeader.vue'
import CalendarHeading from '@/components/ui/calendar/CalendarHeading.vue'
import CalendarNextButton from '@/components/ui/calendar/CalendarNextButton.vue'
import CalendarPrevButton from '@/components/ui/calendar/CalendarPrevButton.vue'

// Card
import Card from '@/components/ui/card/Card.vue'
import CardContent from '@/components/ui/card/CardContent.vue'
import CardDescription from '@/components/ui/card/CardDescription.vue'
import CardFooter from '@/components/ui/card/CardFooter.vue'
import CardHeader from '@/components/ui/card/CardHeader.vue'
import CardTitle from '@/components/ui/card/CardTitle.vue'

// Chart
import ChartCrosshair from '@/components/ui/chart/ChartCrosshair.vue'
import ChartLegend from '@/components/ui/chart/ChartLegend.vue'
import ChartSingleTooltip from '@/components/ui/chart/ChartSingleTooltip.vue'
import ChartTooltip from '@/components/ui/chart/ChartTooltip.vue'

// Chart Area
import AreaChart from '@/components/ui/chart-area/AreaChart.vue'

// Chart Bar
import BarChart from '@/components/ui/chart-bar/BarChart.vue'

// Chart Donut
import DonutChart from '@/components/ui/chart-donut/DonutChart.vue'

// Chart Line
import LineChart from '@/components/ui/chart-line/LineChart.vue'

// Checkbox
import Checkbox from '@/components/ui/checkbox/Checkbox.vue'

// Collapsible
import Collapsible from '@/components/ui/collapsible/Collapsible.vue'
import CollapsibleContent from '@/components/ui/collapsible/CollapsibleContent.vue'
import CollapsibleTrigger from '@/components/ui/collapsible/CollapsibleTrigger.vue'

// Command
import Command from '@/components/ui/command/Command.vue'
import CommandDialog from '@/components/ui/command/CommandDialog.vue'
import CommandEmpty from '@/components/ui/command/CommandEmpty.vue'
import CommandGroup from '@/components/ui/command/CommandGroup.vue'
import CommandInput from '@/components/ui/command/CommandInput.vue'
import CommandItem from '@/components/ui/command/CommandItem.vue'
import CommandList from '@/components/ui/command/CommandList.vue'
import CommandSeparator from '@/components/ui/command/CommandSeparator.vue'
import CommandShortcut from '@/components/ui/command/CommandShortcut.vue'

// Context Menu
import ContextMenu from '@/components/ui/context-menu/ContextMenu.vue'
import ContextMenuCheckboxItem from '@/components/ui/context-menu/ContextMenuCheckboxItem.vue'
import ContextMenuContent from '@/components/ui/context-menu/ContextMenuContent.vue'
import ContextMenuGroup from '@/components/ui/context-menu/ContextMenuGroup.vue'
import ContextMenuItem from '@/components/ui/context-menu/ContextMenuItem.vue'
import ContextMenuLabel from '@/components/ui/context-menu/ContextMenuLabel.vue'
import ContextMenuPortal from '@/components/ui/context-menu/ContextMenuPortal.vue'
import ContextMenuRadioGroup from '@/components/ui/context-menu/ContextMenuRadioGroup.vue'
import ContextMenuRadioItem from '@/components/ui/context-menu/ContextMenuRadioItem.vue'
import ContextMenuSeparator from '@/components/ui/context-menu/ContextMenuSeparator.vue'
import ContextMenuShortcut from '@/components/ui/context-menu/ContextMenuShortcut.vue'
import ContextMenuSub from '@/components/ui/context-menu/ContextMenuSub.vue'
import ContextMenuSubContent from '@/components/ui/context-menu/ContextMenuSubContent.vue'
import ContextMenuSubTrigger from '@/components/ui/context-menu/ContextMenuSubTrigger.vue'
import ContextMenuTrigger from '@/components/ui/context-menu/ContextMenuTrigger.vue'

// Dialog
import Dialog from '@/components/ui/dialog/Dialog.vue'
import DialogClose from '@/components/ui/dialog/DialogClose.vue'
import DialogContent from '@/components/ui/dialog/DialogContent.vue'
import DialogDescription from '@/components/ui/dialog/DialogDescription.vue'
import DialogFooter from '@/components/ui/dialog/DialogFooter.vue'
import DialogHeader from '@/components/ui/dialog/DialogHeader.vue'
import DialogScrollContent from '@/components/ui/dialog/DialogScrollContent.vue'
import DialogTitle from '@/components/ui/dialog/DialogTitle.vue'
import DialogTrigger from '@/components/ui/dialog/DialogTrigger.vue'

// Drawer
import Drawer from '@/components/ui/drawer/Drawer.vue'
import DrawerContent from '@/components/ui/drawer/DrawerContent.vue'
import DrawerDescription from '@/components/ui/drawer/DrawerDescription.vue'
import DrawerFooter from '@/components/ui/drawer/DrawerFooter.vue'
import DrawerHeader from '@/components/ui/drawer/DrawerHeader.vue'
import DrawerOverlay from '@/components/ui/drawer/DrawerOverlay.vue'
import DrawerTitle from '@/components/ui/drawer/DrawerTitle.vue'

// Dropdown Menu
import DropdownMenu from '@/components/ui/dropdown-menu/DropdownMenu.vue'
import DropdownMenuCheckboxItem from '@/components/ui/dropdown-menu/DropdownMenuCheckboxItem.vue'
import DropdownMenuContent from '@/components/ui/dropdown-menu/DropdownMenuContent.vue'
import DropdownMenuGroup from '@/components/ui/dropdown-menu/DropdownMenuGroup.vue'
import DropdownMenuItem from '@/components/ui/dropdown-menu/DropdownMenuItem.vue'
import DropdownMenuLabel from '@/components/ui/dropdown-menu/DropdownMenuLabel.vue'
import DropdownMenuRadioGroup from '@/components/ui/dropdown-menu/DropdownMenuRadioGroup.vue'
import DropdownMenuRadioItem from '@/components/ui/dropdown-menu/DropdownMenuRadioItem.vue'
import DropdownMenuSeparator from '@/components/ui/dropdown-menu/DropdownMenuSeparator.vue'
import DropdownMenuShortcut from '@/components/ui/dropdown-menu/DropdownMenuShortcut.vue'
import DropdownMenuSub from '@/components/ui/dropdown-menu/DropdownMenuSub.vue'
import DropdownMenuSubContent from '@/components/ui/dropdown-menu/DropdownMenuSubContent.vue'
import DropdownMenuSubTrigger from '@/components/ui/dropdown-menu/DropdownMenuSubTrigger.vue'
import DropdownMenuTrigger from '@/components/ui/dropdown-menu/DropdownMenuTrigger.vue'

// Form
import FormControl from '@/components/ui/form/FormControl.vue'
import FormDescription from '@/components/ui/form/FormDescription.vue'
import FormItem from '@/components/ui/form/FormItem.vue'
import FormLabel from '@/components/ui/form/FormLabel.vue'
import FormMessage from '@/components/ui/form/FormMessage.vue'

// Hover Card
import HoverCard from '@/components/ui/hover-card/HoverCard.vue'
import HoverCardContent from '@/components/ui/hover-card/HoverCardContent.vue'
import HoverCardTrigger from '@/components/ui/hover-card/HoverCardTrigger.vue'

// Input
import Input from '@/components/ui/input/Input.vue'

// Label
import Label from '@/components/ui/label/Label.vue'

// Menubar
import Menubar from '@/components/ui/menubar/Menubar.vue'
import MenubarCheckboxItem from '@/components/ui/menubar/MenubarCheckboxItem.vue'
import MenubarContent from '@/components/ui/menubar/MenubarContent.vue'
import MenubarGroup from '@/components/ui/menubar/MenubarGroup.vue'
import MenubarItem from '@/components/ui/menubar/MenubarItem.vue'
import MenubarLabel from '@/components/ui/menubar/MenubarLabel.vue'
import MenubarMenu from '@/components/ui/menubar/MenubarMenu.vue'
import MenubarRadioGroup from '@/components/ui/menubar/MenubarRadioGroup.vue'
import MenubarRadioItem from '@/components/ui/menubar/MenubarRadioItem.vue'
import MenubarSeparator from '@/components/ui/menubar/MenubarSeparator.vue'
import MenubarShortcut from '@/components/ui/menubar/MenubarShortcut.vue'
import MenubarSub from '@/components/ui/menubar/MenubarSub.vue'
import MenubarSubContent from '@/components/ui/menubar/MenubarSubContent.vue'
import MenubarSubTrigger from '@/components/ui/menubar/MenubarSubTrigger.vue'
import MenubarTrigger from '@/components/ui/menubar/MenubarTrigger.vue'

// Navigation Menu
import NavigationMenu from '@/components/ui/navigation-menu/NavigationMenu.vue'
import NavigationMenuContent from '@/components/ui/navigation-menu/NavigationMenuContent.vue'
import NavigationMenuIndicator from '@/components/ui/navigation-menu/NavigationMenuIndicator.vue'
import NavigationMenuItem from '@/components/ui/navigation-menu/NavigationMenuItem.vue'
import NavigationMenuLink from '@/components/ui/navigation-menu/NavigationMenuLink.vue'
import NavigationMenuList from '@/components/ui/navigation-menu/NavigationMenuList.vue'
import NavigationMenuTrigger from '@/components/ui/navigation-menu/NavigationMenuTrigger.vue'
import NavigationMenuViewport from '@/components/ui/navigation-menu/NavigationMenuViewport.vue'

// Pagination
import PaginationContent from '@/components/ui/pagination/PaginationContent.vue'
import PaginationEllipsis from '@/components/ui/pagination/PaginationEllipsis.vue'
import PaginationFirst from '@/components/ui/pagination/PaginationFirst.vue'
import PaginationItem from '@/components/ui/pagination/PaginationItem.vue'
import PaginationLast from '@/components/ui/pagination/PaginationLast.vue'
import PaginationNext from '@/components/ui/pagination/PaginationNext.vue'
import PaginationPrev from '@/components/ui/pagination/PaginationPrev.vue'

// Pin Input
import PinInput from '@/components/ui/pin-input/PinInput.vue'
import PinInputGroup from '@/components/ui/pin-input/PinInputGroup.vue'
import PinInputInput from '@/components/ui/pin-input/PinInputInput.vue'
import PinInputSeparator from '@/components/ui/pin-input/PinInputSeparator.vue'

// Popover
import Popover from '@/components/ui/popover/Popover.vue'
import PopoverContent from '@/components/ui/popover/PopoverContent.vue'
import PopoverTrigger from '@/components/ui/popover/PopoverTrigger.vue'

// Progress
import Progress from '@/components/ui/progress/Progress.vue'

// Radio Group
import RadioGroup from '@/components/ui/radio-group/RadioGroup.vue'
import RadioGroupItem from '@/components/ui/radio-group/RadioGroupItem.vue'

// Range Calendar
import RangeCalendar from '@/components/ui/range-calendar/RangeCalendar.vue'
import RangeCalendarCell from '@/components/ui/range-calendar/RangeCalendarCell.vue'
import RangeCalendarCellTrigger from '@/components/ui/range-calendar/RangeCalendarCellTrigger.vue'
import RangeCalendarGrid from '@/components/ui/range-calendar/RangeCalendarGrid.vue'
import RangeCalendarGridBody from '@/components/ui/range-calendar/RangeCalendarGridBody.vue'
import RangeCalendarGridHead from '@/components/ui/range-calendar/RangeCalendarGridHead.vue'
import RangeCalendarGridRow from '@/components/ui/range-calendar/RangeCalendarGridRow.vue'
import RangeCalendarHeadCell from '@/components/ui/range-calendar/RangeCalendarHeadCell.vue'
import RangeCalendarHeader from '@/components/ui/range-calendar/RangeCalendarHeader.vue'
import RangeCalendarHeading from '@/components/ui/range-calendar/RangeCalendarHeading.vue'
import RangeCalendarNextButton from '@/components/ui/range-calendar/RangeCalendarNextButton.vue'
import RangeCalendarPrevButton from '@/components/ui/range-calendar/RangeCalendarPrevButton.vue'

// Resizable
import ResizableHandle from '@/components/ui/resizable/ResizableHandle.vue'
import ResizablePanelGroup from '@/components/ui/resizable/ResizablePanelGroup.vue'

// Scroll Area
import ScrollArea from '@/components/ui/scroll-area/ScrollArea.vue'
import ScrollBar from '@/components/ui/scroll-area/ScrollBar.vue'

// Select
import Select from '@/components/ui/select/Select.vue'
import SelectContent from '@/components/ui/select/SelectContent.vue'
import SelectGroup from '@/components/ui/select/SelectGroup.vue'
import SelectItem from '@/components/ui/select/SelectItem.vue'
import SelectItemText from '@/components/ui/select/SelectItemText.vue'
import SelectLabel from '@/components/ui/select/SelectLabel.vue'
import SelectScrollDownButton from '@/components/ui/select/SelectScrollDownButton.vue'
import SelectScrollUpButton from '@/components/ui/select/SelectScrollUpButton.vue'
import SelectSeparator from '@/components/ui/select/SelectSeparator.vue'
import SelectTrigger from '@/components/ui/select/SelectTrigger.vue'
import SelectValue from '@/components/ui/select/SelectValue.vue'

// Separator
import Separator from '@/components/ui/separator/Separator.vue'

// Sheet
import Sheet from '@/components/ui/sheet/Sheet.vue'
import SheetClose from '@/components/ui/sheet/SheetClose.vue'
import SheetContent from '@/components/ui/sheet/SheetContent.vue'
import SheetDescription from '@/components/ui/sheet/SheetDescription.vue'
import SheetFooter from '@/components/ui/sheet/SheetFooter.vue'
import SheetHeader from '@/components/ui/sheet/SheetHeader.vue'
import SheetTitle from '@/components/ui/sheet/SheetTitle.vue'
import SheetTrigger from '@/components/ui/sheet/SheetTrigger.vue'

// Skeleton
import Skeleton from '@/components/ui/skeleton/Skeleton.vue'

// Slider
import Slider from '@/components/ui/slider/Slider.vue'

// Switch
import Switch from '@/components/ui/switch/Switch.vue'

// Table
import Table from '@/components/ui/table/Table.vue'
import TableBody from '@/components/ui/table/TableBody.vue'
import TableCaption from '@/components/ui/table/TableCaption.vue'
import TableCell from '@/components/ui/table/TableCell.vue'
import TableEmpty from '@/components/ui/table/TableEmpty.vue'
import TableFooter from '@/components/ui/table/TableFooter.vue'
import TableHead from '@/components/ui/table/TableHead.vue'
import TableHeader from '@/components/ui/table/TableHeader.vue'
import TableRow from '@/components/ui/table/TableRow.vue'

// Tabs
import Tabs from '@/components/ui/tabs/Tabs.vue'
import TabsContent from '@/components/ui/tabs/TabsContent.vue'
import TabsList from '@/components/ui/tabs/TabsList.vue'
import TabsTrigger from '@/components/ui/tabs/TabsTrigger.vue'

// Tags Input
import TagsInput from '@/components/ui/tags-input/TagsInput.vue'
import TagsInputInput from '@/components/ui/tags-input/TagsInputInput.vue'
import TagsInputItem from '@/components/ui/tags-input/TagsInputItem.vue'
import TagsInputItemDelete from '@/components/ui/tags-input/TagsInputItemDelete.vue'
import TagsInputItemText from '@/components/ui/tags-input/TagsInputItemText.vue'

// Textarea
import Textarea from '@/components/ui/textarea/Textarea.vue'

// Toast
import Toast from '@/components/ui/toast/Toast.vue'
import ToastAction from '@/components/ui/toast/ToastAction.vue'
import ToastClose from '@/components/ui/toast/ToastClose.vue'
import ToastDescription from '@/components/ui/toast/ToastDescription.vue'
import Toaster from '@/components/ui/toast/Toaster.vue'
import ToastProvider from '@/components/ui/toast/ToastProvider.vue'
import ToastTitle from '@/components/ui/toast/ToastTitle.vue'
import ToastViewport from '@/components/ui/toast/ToastViewport.vue'

// Toggle
import Toggle from '@/components/ui/toggle/Toggle.vue'

// Toggle Group
import ToggleGroup from '@/components/ui/toggle-group/ToggleGroup.vue'
import ToggleGroupItem from '@/components/ui/toggle-group/ToggleGroupItem.vue'

// Tooltip
import Tooltip from '@/components/ui/tooltip/Tooltip.vue'
import TooltipContent from '@/components/ui/tooltip/TooltipContent.vue'
import TooltipProvider from '@/components/ui/tooltip/TooltipProvider.vue'
import TooltipTrigger from '@/components/ui/tooltip/TooltipTrigger.vue'

export default defineNuxtPlugin((nuxtApp) => {
  // Vee-validate components
  nuxtApp.vueApp.component('Form', Form)
  nuxtApp.vueApp.component('FormField', FormField)

  // Accordion
  nuxtApp.vueApp.component('Accordion', Accordion)
  nuxtApp.vueApp.component('AccordionContent', AccordionContent)
  nuxtApp.vueApp.component('AccordionItem', AccordionItem)
  nuxtApp.vueApp.component('AccordionTrigger', AccordionTrigger)

  // Alert
  nuxtApp.vueApp.component('Alert', Alert)
  nuxtApp.vueApp.component('AlertDescription', AlertDescription)
  nuxtApp.vueApp.component('AlertTitle', AlertTitle)

  // Alert Dialog
  nuxtApp.vueApp.component('AlertDialog', AlertDialog)
  nuxtApp.vueApp.component('AlertDialogAction', AlertDialogAction)
  nuxtApp.vueApp.component('AlertDialogCancel', AlertDialogCancel)
  nuxtApp.vueApp.component('AlertDialogContent', AlertDialogContent)
  nuxtApp.vueApp.component('AlertDialogDescription', AlertDialogDescription)
  nuxtApp.vueApp.component('AlertDialogFooter', AlertDialogFooter)
  nuxtApp.vueApp.component('AlertDialogHeader', AlertDialogHeader)
  nuxtApp.vueApp.component('AlertDialogTitle', AlertDialogTitle)
  nuxtApp.vueApp.component('AlertDialogTrigger', AlertDialogTrigger)

  // Aspect Ratio
  nuxtApp.vueApp.component('AspectRatio', AspectRatio)

  // Auto Form
  nuxtApp.vueApp.component('AutoForm', AutoForm)
  nuxtApp.vueApp.component('AutoFormField', AutoFormField)
  nuxtApp.vueApp.component('AutoFormFieldArray', AutoFormFieldArray)
  nuxtApp.vueApp.component('AutoFormFieldBoolean', AutoFormFieldBoolean)
  nuxtApp.vueApp.component('AutoFormFieldDate', AutoFormFieldDate)
  nuxtApp.vueApp.component('AutoFormFieldEnum', AutoFormFieldEnum)
  nuxtApp.vueApp.component('AutoFormFieldFile', AutoFormFieldFile)
  nuxtApp.vueApp.component('AutoFormFieldInput', AutoFormFieldInput)
  nuxtApp.vueApp.component('AutoFormFieldNumber', AutoFormFieldNumber)
  nuxtApp.vueApp.component('AutoFormFieldObject', AutoFormFieldObject)
  nuxtApp.vueApp.component('AutoFormLabel', AutoFormLabel)

  // Avatar
  nuxtApp.vueApp.component('Avatar', Avatar)
  nuxtApp.vueApp.component('AvatarFallback', AvatarFallback)
  nuxtApp.vueApp.component('AvatarImage', AvatarImage)

  // Badge
  nuxtApp.vueApp.component('Badge', Badge)

  // Breadcrumb
  nuxtApp.vueApp.component('Breadcrumb', Breadcrumb)
  nuxtApp.vueApp.component('BreadcrumbEllipsis', BreadcrumbEllipsis)
  nuxtApp.vueApp.component('BreadcrumbItem', BreadcrumbItem)
  nuxtApp.vueApp.component('BreadcrumbLink', BreadcrumbLink)
  nuxtApp.vueApp.component('BreadcrumbList', BreadcrumbList)
  nuxtApp.vueApp.component('BreadcrumbPage', BreadcrumbPage)
  nuxtApp.vueApp.component('BreadcrumbSeparator', BreadcrumbSeparator)

  // Button
  nuxtApp.vueApp.component('Button', Button)

  // Calendar
  nuxtApp.vueApp.component('Calendar', Calendar)
  nuxtApp.vueApp.component('CalendarCell', CalendarCell)
  nuxtApp.vueApp.component('CalendarCellTrigger', CalendarCellTrigger)
  nuxtApp.vueApp.component('CalendarGrid', CalendarGrid)
  nuxtApp.vueApp.component('CalendarGridBody', CalendarGridBody)
  nuxtApp.vueApp.component('CalendarGridHead', CalendarGridHead)
  nuxtApp.vueApp.component('CalendarGridRow', CalendarGridRow)
  nuxtApp.vueApp.component('CalendarHeadCell', CalendarHeadCell)
  nuxtApp.vueApp.component('CalendarHeader', CalendarHeader)
  nuxtApp.vueApp.component('CalendarHeading', CalendarHeading)
  nuxtApp.vueApp.component('CalendarNextButton', CalendarNextButton)
  nuxtApp.vueApp.component('CalendarPrevButton', CalendarPrevButton)

  // Card
  nuxtApp.vueApp.component('Card', Card)
  nuxtApp.vueApp.component('CardContent', CardContent)
  nuxtApp.vueApp.component('CardDescription', CardDescription)
  nuxtApp.vueApp.component('CardFooter', CardFooter)
  nuxtApp.vueApp.component('CardHeader', CardHeader)
  nuxtApp.vueApp.component('CardTitle', CardTitle)

  // Chart
  nuxtApp.vueApp.component('ChartCrosshair', ChartCrosshair)
  nuxtApp.vueApp.component('ChartLegend', ChartLegend)
  nuxtApp.vueApp.component('ChartSingleTooltip', ChartSingleTooltip)
  nuxtApp.vueApp.component('ChartTooltip', ChartTooltip)
  nuxtApp.vueApp.component('AreaChart', AreaChart)
  nuxtApp.vueApp.component('BarChart', BarChart)
  nuxtApp.vueApp.component('DonutChart', DonutChart)
  nuxtApp.vueApp.component('LineChart', LineChart)

  // Checkbox
  nuxtApp.vueApp.component('Checkbox', Checkbox)

  // Collapsible
  nuxtApp.vueApp.component('Collapsible', Collapsible)
  nuxtApp.vueApp.component('CollapsibleContent', CollapsibleContent)
  nuxtApp.vueApp.component('CollapsibleTrigger', CollapsibleTrigger)

  // Command
  nuxtApp.vueApp.component('Command', Command)
  nuxtApp.vueApp.component('CommandDialog', CommandDialog)
  nuxtApp.vueApp.component('CommandEmpty', CommandEmpty)
  nuxtApp.vueApp.component('CommandGroup', CommandGroup)
  nuxtApp.vueApp.component('CommandInput', CommandInput)
  nuxtApp.vueApp.component('CommandItem', CommandItem)
  nuxtApp.vueApp.component('CommandList', CommandList)
  nuxtApp.vueApp.component('CommandSeparator', CommandSeparator)
  nuxtApp.vueApp.component('CommandShortcut', CommandShortcut)

  // Context Menu
  nuxtApp.vueApp.component('ContextMenu', ContextMenu)
  nuxtApp.vueApp.component('ContextMenuCheckboxItem', ContextMenuCheckboxItem)
  nuxtApp.vueApp.component('ContextMenuContent', ContextMenuContent)
  nuxtApp.vueApp.component('ContextMenuGroup', ContextMenuGroup)
  nuxtApp.vueApp.component('ContextMenuItem', ContextMenuItem)
  nuxtApp.vueApp.component('ContextMenuLabel', ContextMenuLabel)
  nuxtApp.vueApp.component('ContextMenuPortal', ContextMenuPortal)
  nuxtApp.vueApp.component('ContextMenuRadioGroup', ContextMenuRadioGroup)
  nuxtApp.vueApp.component('ContextMenuRadioItem', ContextMenuRadioItem)
  nuxtApp.vueApp.component('ContextMenuSeparator', ContextMenuSeparator)
  nuxtApp.vueApp.component('ContextMenuShortcut', ContextMenuShortcut)
  nuxtApp.vueApp.component('ContextMenuSub', ContextMenuSub)
  nuxtApp.vueApp.component('ContextMenuSubContent', ContextMenuSubContent)
  nuxtApp.vueApp.component('ContextMenuSubTrigger', ContextMenuSubTrigger)
  nuxtApp.vueApp.component('ContextMenuTrigger', ContextMenuTrigger)

  // Dialog
  nuxtApp.vueApp.component('Dialog', Dialog)
  nuxtApp.vueApp.component('DialogClose', DialogClose)
  nuxtApp.vueApp.component('DialogContent', DialogContent)
  nuxtApp.vueApp.component('DialogDescription', DialogDescription)
  nuxtApp.vueApp.component('DialogFooter', DialogFooter)
  nuxtApp.vueApp.component('DialogHeader', DialogHeader)
  nuxtApp.vueApp.component('DialogScrollContent', DialogScrollContent)
  nuxtApp.vueApp.component('DialogTitle', DialogTitle)
  nuxtApp.vueApp.component('DialogTrigger', DialogTrigger)

  // Drawer
  nuxtApp.vueApp.component('Drawer', Drawer)
  nuxtApp.vueApp.component('DrawerContent', DrawerContent)
  nuxtApp.vueApp.component('DrawerDescription', DrawerDescription)
  nuxtApp.vueApp.component('DrawerFooter', DrawerFooter)
  nuxtApp.vueApp.component('DrawerHeader', DrawerHeader)
  nuxtApp.vueApp.component('DrawerOverlay', DrawerOverlay)
  nuxtApp.vueApp.component('DrawerTitle', DrawerTitle)

  // Dropdown Menu
  nuxtApp.vueApp.component('DropdownMenu', DropdownMenu)
  nuxtApp.vueApp.component('DropdownMenuCheckboxItem', DropdownMenuCheckboxItem)
  nuxtApp.vueApp.component('DropdownMenuContent', DropdownMenuContent)
  nuxtApp.vueApp.component('DropdownMenuGroup', DropdownMenuGroup)
  nuxtApp.vueApp.component('DropdownMenuItem', DropdownMenuItem)
  nuxtApp.vueApp.component('DropdownMenuLabel', DropdownMenuLabel)
  nuxtApp.vueApp.component('DropdownMenuRadioGroup', DropdownMenuRadioGroup)
  nuxtApp.vueApp.component('DropdownMenuRadioItem', DropdownMenuRadioItem)
  nuxtApp.vueApp.component('DropdownMenuSeparator', DropdownMenuSeparator)
  nuxtApp.vueApp.component('DropdownMenuShortcut', DropdownMenuShortcut)
  nuxtApp.vueApp.component('DropdownMenuSub', DropdownMenuSub)
  nuxtApp.vueApp.component('DropdownMenuSubContent', DropdownMenuSubContent)
  nuxtApp.vueApp.component('DropdownMenuSubTrigger', DropdownMenuSubTrigger)
  nuxtApp.vueApp.component('DropdownMenuTrigger', DropdownMenuTrigger)

  // Form
  nuxtApp.vueApp.component('FormControl', FormControl)
  nuxtApp.vueApp.component('FormDescription', FormDescription)
  nuxtApp.vueApp.component('FormItem', FormItem)
  nuxtApp.vueApp.component('FormLabel', FormLabel)
  nuxtApp.vueApp.component('FormMessage', FormMessage)

  // Hover Card
  nuxtApp.vueApp.component('HoverCard', HoverCard)
  nuxtApp.vueApp.component('HoverCardContent', HoverCardContent)
  nuxtApp.vueApp.component('HoverCardTrigger', HoverCardTrigger)

  // Input
  nuxtApp.vueApp.component('Input', Input)

  // Label
  nuxtApp.vueApp.component('Label', Label)

  // Menubar
  nuxtApp.vueApp.component('Menubar', Menubar)
  nuxtApp.vueApp.component('MenubarCheckboxItem', MenubarCheckboxItem)
  nuxtApp.vueApp.component('MenubarContent', MenubarContent)
  nuxtApp.vueApp.component('MenubarGroup', MenubarGroup)
  nuxtApp.vueApp.component('MenubarItem', MenubarItem)
  nuxtApp.vueApp.component('MenubarLabel', MenubarLabel)
  nuxtApp.vueApp.component('MenubarMenu', MenubarMenu)
  nuxtApp.vueApp.component('MenubarRadioGroup', MenubarRadioGroup)
  nuxtApp.vueApp.component('MenubarRadioItem', MenubarRadioItem)
  nuxtApp.vueApp.component('MenubarSeparator', MenubarSeparator)
  nuxtApp.vueApp.component('MenubarShortcut', MenubarShortcut)
  nuxtApp.vueApp.component('MenubarSub', MenubarSub)
  nuxtApp.vueApp.component('MenubarSubContent', MenubarSubContent)
  nuxtApp.vueApp.component('MenubarSubTrigger', MenubarSubTrigger)
  nuxtApp.vueApp.component('MenubarTrigger', MenubarTrigger)

  // Navigation Menu
  nuxtApp.vueApp.component('NavigationMenu', NavigationMenu)
  nuxtApp.vueApp.component('NavigationMenuContent', NavigationMenuContent)
  nuxtApp.vueApp.component('NavigationMenuIndicator', NavigationMenuIndicator)
  nuxtApp.vueApp.component('NavigationMenuItem', NavigationMenuItem)
  nuxtApp.vueApp.component('NavigationMenuLink', NavigationMenuLink)
  nuxtApp.vueApp.component('NavigationMenuList', NavigationMenuList)
  nuxtApp.vueApp.component('NavigationMenuTrigger', NavigationMenuTrigger)
  nuxtApp.vueApp.component('NavigationMenuViewport', NavigationMenuViewport)

  // Pagination
  nuxtApp.vueApp.component('PaginationContent', PaginationContent)
  nuxtApp.vueApp.component('PaginationEllipsis', PaginationEllipsis)
  nuxtApp.vueApp.component('PaginationFirst', PaginationFirst)
  nuxtApp.vueApp.component('PaginationItem', PaginationItem)
  nuxtApp.vueApp.component('PaginationLast', PaginationLast)
  nuxtApp.vueApp.component('PaginationNext', PaginationNext)
  nuxtApp.vueApp.component('PaginationPrev', PaginationPrev)

  // Pin Input
  nuxtApp.vueApp.component('PinInput', PinInput)
  nuxtApp.vueApp.component('PinInputGroup', PinInputGroup)
  nuxtApp.vueApp.component('PinInputInput', PinInputInput)
  nuxtApp.vueApp.component('PinInputSeparator', PinInputSeparator)

  // Popover
  nuxtApp.vueApp.component('Popover', Popover)
  nuxtApp.vueApp.component('PopoverContent', PopoverContent)
  nuxtApp.vueApp.component('PopoverTrigger', PopoverTrigger)

  // Progress
  nuxtApp.vueApp.component('Progress', Progress)

  // Radio Group
  nuxtApp.vueApp.component('RadioGroup', RadioGroup)
  nuxtApp.vueApp.component('RadioGroupItem', RadioGroupItem)

  // Range Calendar
  nuxtApp.vueApp.component('RangeCalendar', RangeCalendar)
  nuxtApp.vueApp.component('RangeCalendarCell', RangeCalendarCell)
  nuxtApp.vueApp.component('RangeCalendarCellTrigger', RangeCalendarCellTrigger)
  nuxtApp.vueApp.component('RangeCalendarGrid', RangeCalendarGrid)
  nuxtApp.vueApp.component('RangeCalendarGridBody', RangeCalendarGridBody)
  nuxtApp.vueApp.component('RangeCalendarGridHead', RangeCalendarGridHead)
  nuxtApp.vueApp.component('RangeCalendarGridRow', RangeCalendarGridRow)
  nuxtApp.vueApp.component('RangeCalendarHeadCell', RangeCalendarHeadCell)
  nuxtApp.vueApp.component('RangeCalendarHeader', RangeCalendarHeader)
  nuxtApp.vueApp.component('RangeCalendarHeading', RangeCalendarHeading)
  nuxtApp.vueApp.component('RangeCalendarNextButton', RangeCalendarNextButton)
  nuxtApp.vueApp.component('RangeCalendarPrevButton', RangeCalendarPrevButton)

  // Resizable
  nuxtApp.vueApp.component('ResizableHandle', ResizableHandle)
  nuxtApp.vueApp.component('ResizablePanelGroup', ResizablePanelGroup)

  // Scroll Area
  nuxtApp.vueApp.component('ScrollArea', ScrollArea)
  nuxtApp.vueApp.component('ScrollBar', ScrollBar)

  // Select
  nuxtApp.vueApp.component('Select', Select)
  nuxtApp.vueApp.component('SelectContent', SelectContent)
  nuxtApp.vueApp.component('SelectGroup', SelectGroup)
  nuxtApp.vueApp.component('SelectItem', SelectItem)
  nuxtApp.vueApp.component('SelectItemText', SelectItemText)
  nuxtApp.vueApp.component('SelectLabel', SelectLabel)
  nuxtApp.vueApp.component('SelectScrollDownButton', SelectScrollDownButton)
  nuxtApp.vueApp.component('SelectScrollUpButton', SelectScrollUpButton)
  nuxtApp.vueApp.component('SelectSeparator', SelectSeparator)
  nuxtApp.vueApp.component('SelectTrigger', SelectTrigger)
  nuxtApp.vueApp.component('SelectValue', SelectValue)

  // Separator
  nuxtApp.vueApp.component('Separator', Separator)

  // Sheet
  nuxtApp.vueApp.component('Sheet', Sheet)
  nuxtApp.vueApp.component('SheetClose', SheetClose)
  nuxtApp.vueApp.component('SheetContent', SheetContent)
  nuxtApp.vueApp.component('SheetDescription', SheetDescription)
  nuxtApp.vueApp.component('SheetFooter', SheetFooter)
  nuxtApp.vueApp.component('SheetHeader', SheetHeader)
  nuxtApp.vueApp.component('SheetTitle', SheetTitle)
  nuxtApp.vueApp.component('SheetTrigger', SheetTrigger)

  // Skeleton
  nuxtApp.vueApp.component('Skeleton', Skeleton)

  // Slider
  nuxtApp.vueApp.component('Slider', Slider)

  // Switch
  nuxtApp.vueApp.component('Switch', Switch)

  // Table
  nuxtApp.vueApp.component('Table', Table)
  nuxtApp.vueApp.component('TableBody', TableBody)
  nuxtApp.vueApp.component('TableCaption', TableCaption)
  nuxtApp.vueApp.component('TableCell', TableCell)
  nuxtApp.vueApp.component('TableEmpty', TableEmpty)
  nuxtApp.vueApp.component('TableFooter', TableFooter)
  nuxtApp.vueApp.component('TableHead', TableHead)
  nuxtApp.vueApp.component('TableHeader', TableHeader)
  nuxtApp.vueApp.component('TableRow', TableRow)

  // Tabs
  nuxtApp.vueApp.component('Tabs', Tabs)
  nuxtApp.vueApp.component('TabsContent', TabsContent)
  nuxtApp.vueApp.component('TabsList', TabsList)
  nuxtApp.vueApp.component('TabsTrigger', TabsTrigger)

  // Tags Input
  nuxtApp.vueApp.component('TagsInput', TagsInput)
  nuxtApp.vueApp.component('TagsInputInput', TagsInputInput)
  nuxtApp.vueApp.component('TagsInputItem', TagsInputItem)
  nuxtApp.vueApp.component('TagsInputItemDelete', TagsInputItemDelete)
  nuxtApp.vueApp.component('TagsInputItemText', TagsInputItemText)

  // Textarea
  nuxtApp.vueApp.component('Textarea', Textarea)

  // Toast
  nuxtApp.vueApp.component('Toast', Toast)
  nuxtApp.vueApp.component('ToastAction', ToastAction)
  nuxtApp.vueApp.component('ToastClose', ToastClose)
  nuxtApp.vueApp.component('ToastDescription', ToastDescription)
  nuxtApp.vueApp.component('Toaster', Toaster)
  nuxtApp.vueApp.component('ToastProvider', ToastProvider)
  nuxtApp.vueApp.component('ToastTitle', ToastTitle)
  nuxtApp.vueApp.component('ToastViewport', ToastViewport)

  // Toggle
  nuxtApp.vueApp.component('Toggle', Toggle)

  // Toggle Group
  nuxtApp.vueApp.component('ToggleGroup', ToggleGroup)
  nuxtApp.vueApp.component('ToggleGroupItem', ToggleGroupItem)

  // Tooltip
  nuxtApp.vueApp.component('Tooltip', Tooltip)
  nuxtApp.vueApp.component('TooltipContent', TooltipContent)
  nuxtApp.vueApp.component('TooltipProvider', TooltipProvider)
  nuxtApp.vueApp.component('TooltipTrigger', TooltipTrigger)
})
